package com.slemenceu.taptrack.features.connection.data.repository

import android.util.Log
import com.slemenceu.taptrack.features.connection.data.service.ConnectionManager
import com.slemenceu.taptrack.features.connection.data.utils.intToByte
import com.slemenceu.taptrack.features.connection.domain.models.ConnectionStatus
import com.slemenceu.taptrack.features.connection.domain.repository.ConnectionRepository
import com.slemenceu.taptrack.features.mousepad.data.repository.MouseRepositoryImpl.Command
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketTimeoutException

class ConnectionRepositoryImpl(
    private val connectionManager: ConnectionManager
) : ConnectionRepository {

    private val TAG = "ConnectionRepositoryImpl"

    /**
     * Expose the single source of truth from ConnectionManager.
     */
    override val connectionStatus: StateFlow<ConnectionStatus> =
        connectionManager.connectionStatus

    /**
     * Repository-owned scope for long-lived background work.
     */
    private val scope = CoroutineScope(
        SupervisorJob() + Dispatchers.IO
    )

    private var monitorJob: Job? = null

    override suspend fun connect(
        ip: String,
        port: Int,
        passcode: Int
    ): Result<Unit> {
        return try {
            connectionManager.setConnecting()

            val socket = DatagramSocket()
            socket.soTimeout = 2000

            Log.d(TAG, "Attempting to connect to $ip:$port")

            val address = InetAddress.getByName(ip)

            val buffer = ByteArray(9)
            buffer[0] = Command.CONNECT
            intToByte(passcode).copyInto(buffer, 1)

            val packet = DatagramPacket(
                buffer,
                buffer.size,
                address,
                port
            )

            socket.send(packet)

            val confirmBuffer = ByteArray(1)
            val confirmPacket = DatagramPacket(
                confirmBuffer,
                confirmBuffer.size
            )

            socket.receive(confirmPacket)

            if (confirmBuffer[0] == Command.CONNECT_ACK) {
                socket.soTimeout = 0

                // Store the connected socket.
                connectionManager.setSocket(socket)

                // Start continuous connection monitoring.
                startMonitoring()

                Log.d(TAG, "Connection successful")
                Result.success(Unit)
            } else {
                socket.close()
                connectionManager.setConnectionFailed("Invalid ACK")
                Result.failure(Exception("Invalid ACK"))
            }

        } catch (e: SocketTimeoutException) {
            Log.e(TAG, "Connection timeout", e)
            connectionManager.setConnectionFailed("Connection timeout")
            Result.failure(e)

        } catch (e: Exception) {
            Log.e(TAG, "Connection failed", e)
            connectionManager.setConnectionFailed(
                e.message ?: "Connection failed"
            )
            Result.failure(e)
        }
    }

    override suspend fun disconnect() {
        monitorJob?.cancel()
        connectionManager.disconnect()
    }

    /**
     * Starts a background coroutine that periodically checks
     * whether the socket is still valid.
     */
    private fun startMonitoring() {
        monitorJob?.cancel()

        monitorJob = scope.launch {
            while (isActive) {
                delay(2000)

                val socket = connectionManager.getSocket()

                val stillConnected =
                    socket != null &&
                            !socket.isClosed &&
                            connectionManager.isConnected

                if (!stillConnected) {
                    Log.d(TAG, "Connection lost")
                    connectionManager.disconnect()
                    break
                }
            }
        }
    }

    private object Command {
        const val CONNECT: Byte = 99
        const val CONNECT_ACK: Byte = 100.toByte()
    }
}