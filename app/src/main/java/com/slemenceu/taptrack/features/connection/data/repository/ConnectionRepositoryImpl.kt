package com.slemenceu.taptrack.features.connection.data.repository

import android.util.Log
import com.slemenceu.taptrack.features.connection.data.service.ConnectionManager
import com.slemenceu.taptrack.features.connection.domain.models.ConnectionStatus
import com.slemenceu.taptrack.features.connection.domain.repository.ConnectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext


class ConnectionRepositoryImpl(
    private val connectionManager: ConnectionManager
) : ConnectionRepository {


    val TAG = "ConnectionRepositoryLogs"

    override val connectionStatus: StateFlow<ConnectionStatus> =
        connectionManager.connectionStatus


    override suspend fun connect(ip: String, port: Int): Result<Int> {
        return connectionManager.connect(ip, port)

    }

    override suspend fun disconnect() {
        connectionManager.disconnect()
    }
}