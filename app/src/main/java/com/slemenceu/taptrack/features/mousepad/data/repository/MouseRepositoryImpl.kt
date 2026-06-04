package com.slemenceu.taptrack.features.mousepad.data.repository


import android.util.Log
import com.slemenceu.taptrack.features.connection.data.service.ConnectionManager
import com.slemenceu.taptrack.features.connection.domain.models.ConnectionStatus
import com.slemenceu.taptrack.features.mousepad.domain.MouseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.net.DatagramPacket

class MouseRepositoryImpl(
    private val connectionManager: ConnectionManager
) : MouseRepository {

    private val TAG = "MouseRepositoryLogs"

    override val connectionStatus: StateFlow<ConnectionStatus> =
        connectionManager.connectionStatus

    private val moveBytes = ByteArray(9)
    private val movePacket = DatagramPacket(moveBytes, moveBytes.size)
    private val clickBytes = ByteArray(2)

    private object Command {
        const val MOVE: Byte = 0
        const val CLICK: Byte = 1
        const val SCROLL: Byte = 2
    }

    override suspend fun sendMouseMove(dx: Int, dy: Int) {
        if (!connectionManager.isConnected) return
        connectionManager.updateMousePosition(dx, dy)
    }

    override suspend fun sendClick(rightClick: Boolean) {
        val tcpStream = connectionManager.getTcpStream() ?: return
        if (!connectionManager.isConnected) return

        withContext(Dispatchers.IO) {
            try {
                clickBytes[0] = Command.CLICK
                clickBytes[1] = if (rightClick) 1.toByte() else 0.toByte()

                tcpStream.write(clickBytes)
                tcpStream.flush()
                Log.d(TAG, "Mouse click sent: rightClick=$rightClick")
            } catch (e: Exception) {
                connectionManager.setConnectionFailed("Control pipeline lost.")
            }
        }
    }

    override suspend fun sendScroll(dy: Int) {
        if (!connectionManager.isConnected) return
        connectionManager.updateScrollPosition(dy)
    }

    override suspend fun disconnect() {
        connectionManager.disconnect()
    }
}
