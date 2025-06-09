package com.slemenceu.taptrack.mousepad.ui.home_screen

data class HomeUiState(
    val ssid: String = "",
    val isConnected: Boolean = false,
    val permissions: PermissionUiState = PermissionUiState()
)
data class PermissionUiState(
    val allPermissionGranted: Boolean = false,
    val isLocationGranted: Boolean = false,
    val isWifiStateGranted: Boolean = false,
)