package com.slemenceu.taptrack.mousepad.data.repository

import com.slemenceu.taptrack.mousepad.domain.MouseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketTimeoutException
import java.nio.ByteBuffer

class MouseRepositoryImpl : MouseRepository {

    private val port = 9999
    private val ip = "192.168.1.5"
    private val address = InetAddress.getByName(ip)

    private val mutex = Mutex()
    private var socket: DatagramSocket? = null
    private var isOtpAuthenticated = false

    private object Command {
        const val MOVE: Byte = 0
        const val LEFT_CLICK: Byte = 1
        const val RIGHT_CLICK: Byte = 2
        const val CONNECT: Byte = 99
        const val CONNECT_ACK: Byte = 100.toByte()
    }

    private fun ensureSocketInitialized() {
        if (socket == null || socket?.isClosed == true) {
            socket = DatagramSocket()
        }
    }

    override suspend fun connectToMousepad(passcode: Int): Boolean = withContext(Dispatchers.IO) {
        if (isOtpAuthenticated) return@withContext true

        try {
            ensureSocketInitialized()
            socket?.soTimeout = 2000

            val buffer = ByteArray(9)
            buffer[0] = Command.CONNECT
            intToByte(passcode).copyInto(buffer, 1)
            val packet = DatagramPacket(buffer, buffer.size, address, port)
            socket?.send(packet)

            val confirmBuffer = ByteArray(1)
            val confirmPacket = DatagramPacket(confirmBuffer, confirmBuffer.size)
            socket?.receive(confirmPacket)

            val success = confirmBuffer[0] == Command.CONNECT_ACK
            if (success) {
                isOtpAuthenticated = true
                socket?.soTimeout = 0 // reset timeout
            }

            return@withContext success
        } catch (e: SocketTimeoutException) {
            false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun sendMouseMove(dx: Int, dy: Int) {
        mutex.withLock {
            ensureSocketInitialized()

            val buffer = ByteBuffer.allocate(9)
            buffer.put(Command.MOVE)
            buffer.putInt(dx)
            buffer.putInt(dy)
            val packet = DatagramPacket(buffer.array(), buffer.position(), address, port)

            withContext(Dispatchers.IO) {
                socket?.send(packet)
            }
        }
    }

    override suspend fun sendClick(rightClick: Boolean) {
        mutex.withLock {
            ensureSocketInitialized()

            val buffer = ByteBuffer.allocate(1)
            buffer.put(if (rightClick) Command.RIGHT_CLICK else Command.LEFT_CLICK)
            val packet = DatagramPacket(buffer.array(), buffer.position(), address, port)

            withContext(Dispatchers.IO) {
                socket?.send(packet)
            }
        }
    }

     override suspend fun disconnectFromMousePad() {
        withContext(Dispatchers.IO) {
            socket?.close()
            socket = null
            isOtpAuthenticated = false
        }
    }

    private fun intToByte(value: Int): ByteArray {
        return ByteBuffer.allocate(4).putInt(value).array()
    }

}
