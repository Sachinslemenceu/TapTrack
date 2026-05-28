package com.slemenceu.taptrack.features.mousepad.data.repository


import android.R.attr.delay
import android.util.Log
import androidx.compose.material3.contentColorFor
import com.slemenceu.taptrack.features.connection.data.service.ConnectionManager
import com.slemenceu.taptrack.features.connection.domain.models.ConnectionStatus
import com.slemenceu.taptrack.features.mousepad.domain.MouseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.DatagramPacket
import java.nio.ByteBuffer

class MouseRepositoryImpl(
    private val connectionManager: ConnectionManager
) : MouseRepository {

    private val TAG = "MouseRepositoryLogs"

    override val connectionStatus: StateFlow<ConnectionStatus> =
        connectionManager.connectionStatus

    // 1. PRE-ALLOCATED BUFFERS (Zero-Allocation Strategy)
    // We define these once at the class level so we don't create
    // new objects every time the user moves their finger.
    private val moveBytes = ByteArray(9)
    private val movePacket = DatagramPacket(moveBytes, moveBytes.size)
    private val clickBytes = ByteArray(2)

    private object Command {
        const val MOVE: Byte = 0
        const val CLICK: Byte = 1
    }

    @Volatile
    private var latestDx = 0

    @Volatile
    private var latestDy = 0
    private var senderJob: Job? = null


    override suspend fun sendMouseMove(dx: Int, dy: Int) {
//        // 1. Validation (Keep this on the calling thread for speed)
//        val udp = connectionManager.getUdpSocket() ?: return
//        val target = connectionManager.getTargetAddress() ?: return
//        if (!connectionManager.isConnected) return
//
//        // 2. Prepare data (Keep this on calling thread - Zero Allocation)
//        moveBytes[0] = Command.MOVE
//        moveBytes[1] = (dx shr 24).toByte()
//        moveBytes[2] = (dx shr 16).toByte()
//        moveBytes[3] = (dx shr 8).toByte()
//        moveBytes[4] = dx.toByte()
//        moveBytes[5] = (dy shr 24).toByte()
//        moveBytes[6] = (dy shr 16).toByte()
//        moveBytes[7] = (dy shr 8).toByte()
//        moveBytes[8] = dy.toByte()
//
//        movePacket.address = target
//        movePacket.port = 9999
//
//        // 3. Move to IO Dispatcher for the actual network hit
////        withContext(Dispatchers.IO) {
//        try {
//            udp.send(movePacket)
//        } catch (e: Exception) {
//            // Improved logging to see the ACTUAL error
//            Log.e(TAG, "UDP Send Failed: ${e}")
//        }
////        }
        if (!connectionManager.isConnected) return

        connectionManager.updateMousePosition(dx, dy)

//        latestDx = dx
//        latestDy = dy
    }

    override suspend fun sendClick(rightClick: Boolean) {
        val tcpStream = connectionManager.getTcpStream() ?: return
        if (!connectionManager.isConnected) return

        withContext(Dispatchers.IO) {
            try {
                // [CMD, MODIFIER]
                clickBytes[0] = Command.CLICK
                clickBytes[1] = if (rightClick) 1.toByte() else 0.toByte()

                tcpStream.write(clickBytes)
                tcpStream.flush() // Crucial: Send immediately, don't buffer!
                Log.d(TAG, "Mouse click sent: rightClick=$rightClick")
            } catch (e: Exception) {
                connectionManager.setConnectionFailed("Control pipeline lost.")
            }
        }
    }

    override suspend fun disconnect() {
        connectionManager.disconnect()
    }




}