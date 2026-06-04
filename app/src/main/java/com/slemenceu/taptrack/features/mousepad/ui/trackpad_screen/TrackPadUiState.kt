package com.slemenceu.taptrack.features.mousepad.ui.trackpad_screen

import com.slemenceu.taptrack.features.connection.domain.models.ConnectionStatus

data class TrackPadUiState(
    val deviceName: String = "",
    val connectionStatus: ConnectionStatus = ConnectionStatus.Disconnected,
    val latency: Int = 0
)
