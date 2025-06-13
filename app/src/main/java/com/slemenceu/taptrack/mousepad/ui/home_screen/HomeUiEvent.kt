package com.slemenceu.taptrack.mousepad.ui.home_screen

sealed class HomeUiEvent {
    object onLogOutClicked : HomeUiEvent()
    object onOpenWifiSettings : HomeUiEvent()
    object startWifiTrackingEvent: HomeUiEvent()
    object stopWifiTrackingEvent: HomeUiEvent()
    object loadInitialWifiInfo: HomeUiEvent()
    object onNavigateToMousepad: HomeUiEvent()
    data class onConnectToMousepad(val passcode: Int): HomeUiEvent()
    data class onPermissionResult(val result: Map<String, Boolean>) : HomeUiEvent()

}