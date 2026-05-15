package com.slemenceu.taptrack.features.mousepad.domain

import com.slemenceu.taptrack.features.connection.domain.models.ConnectionStatus
import kotlinx.coroutines.flow.StateFlow

interface MouseRepository {
    val connectionStatus: StateFlow<ConnectionStatus>
    suspend fun disconnect()
    suspend fun sendMouseMove(dx: Int, dy: Int)
    suspend fun sendClick(rightClick: Boolean)
    suspend fun measureUdpLatency(): Long
}