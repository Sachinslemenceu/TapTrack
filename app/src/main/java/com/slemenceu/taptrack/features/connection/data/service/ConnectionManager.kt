package com.slemenceu.taptrack.features.connection.data.service

import com.slemenceu.taptrack.features.connection.domain.models.ConnectionStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.net.DatagramSocket
import java.net.Socket

class ConnectionManager {

    private var socket: DatagramSocket? = null

    private val _connectionStatus =
        MutableStateFlow<ConnectionStatus>(
            ConnectionStatus.Disconnected
        )

    val connectionStatus: StateFlow<ConnectionStatus> =
        _connectionStatus.asStateFlow()

    val isConnected: Boolean
        get() = socket?.isClosed == false &&
                _connectionStatus.value is ConnectionStatus.Connected

    fun setSocket(socket: DatagramSocket) {
        this.socket = socket
        _connectionStatus.value = ConnectionStatus.Connected
    }

    fun getSocket(): DatagramSocket? = socket

    fun setConnecting() {
        _connectionStatus.value = ConnectionStatus.Connecting
    }

    fun setConnectionFailed(message: String) {
        _connectionStatus.value = ConnectionStatus.Failed(message)
    }

    fun disconnect() {
        socket?.close()
        socket = null
        _connectionStatus.value = ConnectionStatus.Disconnected
    }
}