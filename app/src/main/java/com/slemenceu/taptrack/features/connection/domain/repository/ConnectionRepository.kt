package com.slemenceu.taptrack.features.connection.domain.repository

import com.slemenceu.taptrack.features.connection.domain.models.ConnectionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ConnectionRepository {

    val connectionStatus: StateFlow<ConnectionStatus>
    val latency: StateFlow<Int?>
    suspend fun connect(ip: String, port: Int): Result<Int>
    suspend fun disconnect()
}