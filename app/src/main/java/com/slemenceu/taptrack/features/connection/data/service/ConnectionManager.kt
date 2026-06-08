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

    private val _latency = MutableStateFlow<Int?>(null)
    val latency = _latency.asStateFlow()
    val isConnected: Boolean
        get() = tcpSocket?.isClosed == false && _connectionStatus.value is ConnectionStatus.Connected

    // Exposed for MouseRepositoryImpl
    fun getTcpStream() = tcpOutputStream
    fun getUdpSocket() = udpSocket
    fun getTargetAddress() = targetAddress

    private val moveBytes = ByteArray(9)
    private val movePacket = DatagramPacket(moveBytes, moveBytes.size)

    private val scrollBytes = ByteArray(5)
    private val scrollPacket = DatagramPacket(scrollBytes, scrollBytes.size)

    @Volatile
    private var latestDx = 0

    @Volatile
    private var latestDy = 0

    @Volatile
    private var latestScrollDy = 0

    private var senderJob: Job? = null

    private object Command {
        const val MOVE: Byte = 0
        const val CLICK: Byte = 1
        const val SCROLL: Byte = 2
    }


    suspend fun connect(host: String, port: Int): Result<Int> {
        return withContext(Dispatchers.IO) {
            try {
                // Prevent concurrent connection attempts
                if (_connectionStatus.value is ConnectionStatus.Connecting) {
                    return@withContext Result.failure(Exception("Connection already in progress"))
                }

                cleanup() // Ensure fresh start
                _connectionStatus.value = ConnectionStatus.Connecting()

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


                // 4. Start Monitoring Loop
                connectionScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
                val latency = measureUDPLatency()
                
                _connectionStatus.value =
                    ConnectionStatus.Connecting(step = ConnectionStep.ESTABLISHING_UDP_CONNECTION)
                delay(500)
                _connectionStatus.value =
                    ConnectionStatus.Connecting(step = ConnectionStep.VERIFYING_LATENCY)
                delay(500)
                
                if (latency == -1L) {
                    return@withContext Result.failure(
                        Exception("UDP Latency Measurement Failed")
                    )
                }
                
                _connectionStatus.value = ConnectionStatus.Connected
                startHeartbeatMonitor()
                startLatencyMonitor()
                startRealtimeSender()
                return@withContext Result.success(latency.toInt())
            } catch (e: Exception) {
                val error = e.localizedMessage ?: "Connection failed"
                Log.e(TAG, "Connection failed: $error")
                setConnectionFailed(error)
                return@withContext Result.failure(e)
            }
        }
    }
    private fun startHeartbeatMonitor() {

        connectionScope?.launch {

            try {

                tcpSocket?.soTimeout = 5000

                val outputStream = tcpSocket?.getOutputStream()
                val inputStream = tcpSocket?.getInputStream()

                val ping = byteArrayOf(95)
                val expectedPong = 96.toByte()

                var retry = 0

                while (isActive && isConnected) {

                    // Send PING
                    outputStream?.write(ping)
                    outputStream?.flush()

                    val inputBuffer = ByteArray(1)

                    val bytesRead = inputStream?.read(inputBuffer)

                    // Socket closed
                    if (bytesRead == -1) {
                        setConnectionFailed("Server disconnected.")
                        break
                    }

                    val response = inputBuffer[0]

                    // Valid pong
                    if (response == expectedPong) {

                        retry = 0
                        Log.d(TAG, "ping-pong: success")

                    } else {

                        retry++

                        Log.e(TAG, "Invalid heartbeat response")

                        if (retry >= 3) {
                            setConnectionFailed("Heartbeat failed.")
                            break
                        }
                    }

                    delay(2000)
                }

            } catch (e: Exception) {

                if (isConnected) {
                    setConnectionFailed("Connection lost: ${e.message}")
                }
            }
        }
    }

    private fun startLatencyMonitor() {
        connectionScope?.launch {
            while (isConnected) {
                val currentLatency = measureUDPLatency()
                if (currentLatency != -1L) {
                    _latency.value = currentLatency.toInt()
                }
                delay(15000)
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
        senderJob?.cancel()
        senderJob = null
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
            Log.e(TAG, "UDP Latency Measurement Failed: ${e}")
            -1
        }
    }

    fun updateMousePosition(dx: Int, dy: Int) {
        latestDx = dx
        latestDy = dy
    }

    fun updateScrollPosition(dy: Int) {
        latestScrollDy = dy
    }

    private fun startRealtimeSender() {

        senderJob?.cancel()

        senderJob = CoroutineScope(
            Dispatchers.IO + SupervisorJob()
        ).launch {

            val udp = getUdpSocket() ?: return@launch

            val target = getTargetAddress() ?: return@launch

            while (isActive) {

                try {
                    val dx = latestDx
                    val dy = latestDy
                    val sDy = latestScrollDy

                    // Send Move if any
                    if (dx != 0 || dy != 0) {

                        moveBytes[0] = Command.MOVE

                        moveBytes[1] = (dx shr 24).toByte()
                        moveBytes[2] = (dx shr 16).toByte()
                        moveBytes[3] = (dx shr 8).toByte()
                        moveBytes[4] = dx.toByte()

                        moveBytes[5] = (dy shr 24).toByte()
                        moveBytes[6] = (dy shr 16).toByte()
                        moveBytes[7] = (dy shr 8).toByte()
                        moveBytes[8] = dy.toByte()

                        movePacket.address = target
                        movePacket.port = 9999

                        udp.send(movePacket)

                        latestDx = 0
                        latestDy = 0
                    }

                    // Send Scroll if any
                    if (sDy != 0) {
                        scrollBytes[0] = Command.SCROLL
                        scrollBytes[1] = (sDy shr 24).toByte()
                        scrollBytes[2] = (sDy shr 16).toByte()
                        scrollBytes[3] = (sDy shr 8).toByte()
                        scrollBytes[4] = sDy.toByte()

                        scrollPacket.address = target
                        scrollPacket.port = 9999

                        udp.send(scrollPacket)

                        latestScrollDy = 0
                    }

                } catch (e: Exception) {

                    Log.e(TAG, "UDP Send Failed", e)
                }

                delay(8) // ~120Hz
            }
        }
    }
}
