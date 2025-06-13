package com.slemenceu.taptrack.mousepad.ui.home_screen

data class HomeUiState(
    val ssid: String = "",
    val isConnected: Boolean = false,
    val permissions: PermissionUiState = PermissionUiState(),
    val mousepad: MousepadUiState = MousepadUiState()
)
data class PermissionUiState(
    val allPermissionGranted: Boolean = false,
    val isLocationGranted: Boolean = false,
    val isWifiStateGranted: Boolean = false,
)

data class MousepadUiState(
    val isConnected: Boolean = false,
)