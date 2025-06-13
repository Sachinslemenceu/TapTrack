package com.slemenceu.taptrack.mousepad.data.repository

import com.slemenceu.taptrack.mousepad.domain.MouseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.nio.ByteBuffer

class MouseRepositoryImpl: MouseRepository {

    val port = 9999
    val ip = "192.168.1.5"
    val address = InetAddress.getByName(ip)
    val socket = DatagramSocket()
    override suspend fun connectToMousepad(passcode: Int): Boolean = withContext(Dispatchers.IO) {
        sendPasscode(passcode, socket, address, port)
    }

    private fun sendPasscode(passcode: Int,socket: DatagramSocket, address: InetAddress, port: Int): Boolean {
        val buffer = ByteArray(9)
        buffer[0] = 99
        intToByte(passcode).copyInto(buffer, 1)
        val packet = DatagramPacket(buffer, buffer.size, address, port)
        socket.send(packet)
        val confirmBuffer = ByteArray(1)
        val confirmPacket = DatagramPacket(confirmBuffer, confirmBuffer.size)
        socket.receive(confirmPacket)
        return confirmBuffer[0] == 100.toByte()
    }

    private fun intToByte(value: Int): ByteArray{
        return ByteBuffer.allocate(4).putInt(value).array()
    }
    override suspend fun disconnectFromMousePad(){
        socket.close()
    }

}