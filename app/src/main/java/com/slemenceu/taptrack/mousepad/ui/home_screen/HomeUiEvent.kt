package com.slemenceu.taptrack.mousepad.ui.home_screen

import android.app.Activity

sealed class HomeUiEvent {
    object onOpenWifiSettings : HomeUiEvent()
    object startWifiTrackingEvent: HomeUiEvent()
    object stopWifiTrackingEvent: HomeUiEvent()
    object loadInitialWifiInfo: HomeUiEvent()
    object onNavigateToMousepad: HomeUiEvent()
    data class onPermissionResult(val result: Map<String, Boolean>) : HomeUiEvent()
    data class onScannedResult(val result: String) : HomeUiEvent()
    data class onOpenScanner(val activity: Activity) : HomeUiEvent()
    object onScanCancelled: HomeUiEvent()
    object onPcGuideClicked: HomeUiEvent()
}