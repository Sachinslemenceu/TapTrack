package com.slemenceu.taptrack.features.connection.data.service


import android.util.Log
import com.slemenceu.taptrack.features.connection.domain.models.ConnectionStatus
import com.slemenceu.taptrack.features.connection.domain.models.ConnectionStep
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.OutputStream
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket
import java.nio.ByteBuffer


class ConnectionManager {
    private val TAG = "ConnectionManager"

    // Sockets
    private var tcpSocket: Socket? = null
    private var udpSocket: DatagramSocket? = null

    // Cached references for MouseRepository to use (Zero-Allocation access)
    private var tcpOutputStream: OutputStream? = null
    private var targetAddress: InetAddress? = null

    private var connectionScope: CoroutineScope? = null

    private val _connectionStatus =
        MutableStateFlow<ConnectionStatus>(ConnectionStatus.Disconnected)
    val connectionStatus = _connectionStatus.asStateFlow()

    val isConnected: Boolean
        get() = tcpSocket?.isClosed == false && _connectionStatus.value is ConnectionStatus.Connected

    // Exposed for MouseRepositoryImpl
    fun getTcpStream() = tcpOutputStream
    fun getUdpSocket() = udpSocket
    fun getTargetAddress() = targetAddress


    suspend fun connect(host: String, port: Int): Result<Int> {
        return withContext(Dispatchers.IO) {
            try {
                _connectionStatus.value = ConnectionStatus.Connecting()
                cleanup() // Ensure fresh start

                targetAddress = InetAddress.getByName(host)

                // 1. Establish TCP Control Channel (Port 9998)
                tcpSocket = Socket().apply {
                    // Critical for low latency: Disable Nagle's algorithm
                    tcpNoDelay = true
                    // Set a timeout for the initial connection attempt
                    connect(InetSocketAddress(targetAddress, port), 5000)
                }
                tcpOutputStream = tcpSocket?.getOutputStream()

                // 2. Establish UDP High-Frequency Channel (Port 9999)
                udpSocket = DatagramSocket()

                // 3. Optional: Perform Handshake via TCP (More reliable than UDP handshake)
                // For now, we assume connection success if the TCP socket opens

                _connectionStatus.value = ConnectionStatus.Connected

                // 4. Start Monitoring Loop
                connectionScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
                startTcpMonitor()
                val latency = measureUDPLatency()
                delay(1000)
                _connectionStatus.value = ConnectionStatus.Connecting(step = ConnectionStep.ESTABLISHING_UDP_CONNECTION)
                delay(1000)
                _connectionStatus.value = ConnectionStatus.Connecting(step = ConnectionStep.VERIFYING_LATENCY)
                delay(1000)
                if (latency == -1L) Result.failure(Exception("UDP Latency Measurement Failed")) else Result.success(latency.toInt())
            } catch (e: Exception) {
                val error = e.localizedMessage ?: "Connection failed"
                Log.e(TAG, "Connection failed: $error")
                setConnectionFailed(error)
                Result.failure(e)
            }
        }
    }

    /**
     * Watches the TCP socket. If the server closes the connection or the network drops,
     * this loop will terminate, triggering a cleanup.
     */
    private fun startTcpMonitor() {
        connectionScope?.launch {
            try {
                val inputStream = tcpSocket?.getInputStream()
                val buffer = ByteArray(1)
                while (isActive && isConnected) {
                    // read() blocks until data is available or socket closes
                    if (inputStream?.read(buffer) == -1) {
                        setConnectionFailed("Server closed the connection.")
                        break
                    }
                }
            } catch (e: Exception) {
                if (isConnected) setConnectionFailed("Pipeline lost: ${e.message}")
            }
        }
    }

    fun setConnectionFailed(message: String) {
        _connectionStatus.value = ConnectionStatus.Failed(message)
        cleanup()
    }

    fun disconnect() {
        _connectionStatus.value = ConnectionStatus.Disconnected
        cleanup()
    }

    private fun cleanup() {
        connectionScope?.cancel()
        connectionScope = null
        runCatching { tcpSocket?.close() }
        runCatching { udpSocket?.close() }
        tcpSocket = null
        udpSocket = null
        tcpOutputStream = null
        targetAddress = null
    }

    private fun measureUDPLatency(): Long {
        val udp = getUdpSocket() ?: return -1
        val target = getTargetAddress() ?: return -1
        val startTime = System.currentTimeMillis()
        val pingBuffer = ByteBuffer.allocate(9).apply {
            put(95.toByte()) // Command 95 = Ping
            putLong(startTime)
        }.array()

        val packet = DatagramPacket(pingBuffer, pingBuffer.size, target, 9999)
        return try {
            udp.send(packet)
            // Wait for response (simplified)
            val responseBuffer = ByteArray(9)
            val responsePacket = DatagramPacket(responseBuffer, responseBuffer.size)
            udp.soTimeout = 1000 // Don't wait forever
            udp.receive(responsePacket)

            val endTime = System.currentTimeMillis()
            endTime - startTime
        } catch (e: Exception) {
            Log.e(TAG, "UDP Latency Measurement Failed: ${e.localizedMessage}")
            -1
        }
    }
}