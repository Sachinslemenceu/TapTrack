package com.slemenceu.taptrack.mousepad.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
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

class MouseRepositoryImpl(private val context: Context) : MouseRepository {
    private val TAG = "MouseRepositoryLogs"
    private val PREFS_NAME = "mousepad_config"
    private val KEY_IP = "server_ip"
    private val KEY_PORT = "server_port"
    private val DEFAULT_IP = "192.168.1.4"
    private val DEFAULT_PORT = 9999

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // Lazy load to allow config changes without restarting
    private val port: Int
        get() = prefs.getInt(KEY_PORT, DEFAULT_PORT)
    private val ip: String
        get() = prefs.getString(KEY_IP, DEFAULT_IP) ?: DEFAULT_IP
    private val address: InetAddress
        get() = try {
            InetAddress.getByName(ip)
        } catch (e: Exception) {
            Log.e(TAG, "Invalid IP address: $ip", e)
            InetAddress.getByName(DEFAULT_IP)
        }

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
            Log.d(TAG, "Initializing new DatagramSocket for $ip:$port")
            socket = DatagramSocket()
        }
    }

    override suspend fun setServerConfig(ip: String, port: Int) = withContext(Dispatchers.IO) {
        Log.d(TAG, "Setting server config - IP: $ip, Port: $port")
        prefs.edit().apply {
            putString(KEY_IP, ip)
            putInt(KEY_PORT, port)
        }.apply()
        // Reset socket so it uses new config
        socket?.close()
        socket = null
        isOtpAuthenticated = false
    }

    override suspend fun connectToMousepad(passcode: Int): Boolean = withContext(Dispatchers.IO) {
        if (isOtpAuthenticated) {
            Log.d(TAG, "Already authenticated, skipping connection")
            return@withContext true
        }

        try {
            ensureSocketInitialized()
            socket?.soTimeout = 2000
            Log.d(TAG, "Attempting to connect to $ip:$port with passcode: $passcode")

            val buffer = ByteArray(9)
            buffer[0] = Command.CONNECT
            intToByte(passcode).copyInto(buffer, 1)
            val packet = DatagramPacket(buffer, buffer.size, address, port)
            Log.d(TAG, "Sending connect packet")
            socket?.send(packet)

            val confirmBuffer = ByteArray(1)
            val confirmPacket = DatagramPacket(confirmBuffer, confirmBuffer.size)
            socket?.receive(confirmPacket)
            Log.d(TAG, "Received connect response: ${confirmBuffer[0]}")
            val success = confirmBuffer[0] == Command.CONNECT_ACK
            if (success) {
                isOtpAuthenticated = true
                socket?.soTimeout = 0 // reset timeout
                Log.d(TAG, "Connection successful to $ip:$port")
            } else {
                Log.d(TAG, "Connection failed: invalid ack")
            }

            return@withContext success
        } catch (e: SocketTimeoutException) {
            Log.e(TAG, "Connection timeout to $ip:$port", e)
            false
        } catch (e: Exception) {
            Log.e(TAG, "Connection failed to $ip:$port", e)
            e.printStackTrace()
            false
        }
    }

    override suspend fun connectToMousepadWithConfig(ip: String, port: Int, passcode: Int): Boolean {
        setServerConfig(ip, port)
        return connectToMousepad(passcode)
    }

    override suspend fun sendMouseMove(dx: Int, dy: Int) {
        mutex.withLock {
            ensureSocketInitialized()

            val buffer = ByteBuffer.allocate(9)
            buffer.put(Command.MOVE)
            buffer.putInt(dx)
            buffer.putInt(dy)
            val packet = DatagramPacket(buffer.array(), buffer.position(), address, port)
            Log.d(TAG, "Sending mouse move: dx=$dx, dy=$dy")

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
            Log.d(TAG, "Sending click: right=$rightClick")

            withContext(Dispatchers.IO) {
                socket?.send(packet)
            }
        }
    }

     override suspend fun disconnectFromMousePad() {
        withContext(Dispatchers.IO) {
            Log.d(TAG, "Disconnecting from mousepad")
            socket?.close()
            socket = null
            isOtpAuthenticated = false
        }
    }

    private fun intToByte(value: Int): ByteArray {
        return ByteBuffer.allocate(4).putInt(value).array()
    }

}
