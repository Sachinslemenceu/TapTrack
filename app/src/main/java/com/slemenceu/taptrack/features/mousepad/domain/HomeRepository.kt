package com.slemenceu.taptrack.features.mousepad.domain

interface HomeRepository {
    fun startWifiTracking(onSsidDetected: (String) -> Unit)
    fun stopWifiTracking()
    fun openWifiSettings()
    fun getInitialSsid(): String?
}