package com.slemenceu.taptrack.features.mousepad.ui.home_screen

import com.slemenceu.taptrack.features.connection.domain.models.ConnectionStatus
import com.slemenceu.taptrack.features.mousepad.ui.home_screen.composables.ConnectionStatus

data class HomeUiState(
    val userName: String = "",
    val ssid: String = "",
    val isConnected: Boolean = false,
    val connectionStatus: ConnectionStatus = ConnectionStatus.Disconnected,
    val connectionprogress: Float = 0f,
    val latency: Int? = null,
    val permissions: PermissionUiState = PermissionUiState(),
    val mousepad: MousepadUiState = MousepadUiState(),
    val isFirstTime: Boolean = true,
    val deviceName: String = "",
    val networkName: String = ""
)

data class PermissionUiState(
    val allPermissionGranted: Boolean = false,
    val isLocationGranted: Boolean = false,
    val isWifiStateGranted: Boolean = false,
    val isCameraGranted: Boolean = false
)

data class MousepadUiState(
    val isConnected: Boolean = false,
)