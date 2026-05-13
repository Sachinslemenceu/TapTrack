package com.slemenceu.taptrack.features.mousepad.ui.home_screen

data class HomeUiState(
    val ssid: String = "",
    val isConnected: Boolean = false,
    val permissions: PermissionUiState = PermissionUiState(),
    val mousepad: MousepadUiState = MousepadUiState(),
    val isFirstTime: Boolean = true
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