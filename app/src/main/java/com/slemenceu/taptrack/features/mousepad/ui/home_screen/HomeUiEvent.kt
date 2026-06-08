package com.slemenceu.taptrack.features.mousepad.ui.home_screen

sealed class HomeUiEvent {
    data class OnPermissionResult(val result: Map<String, Boolean>) : HomeUiEvent()

    data class Connect(val connectionInfo: String): HomeUiEvent()
}