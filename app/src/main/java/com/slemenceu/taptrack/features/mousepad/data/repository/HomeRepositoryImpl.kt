package com.slemenceu.taptrack.features.mousepad.data.repository

import com.slemenceu.taptrack.features.mousepad.data.services.WifiService
import com.slemenceu.taptrack.features.mousepad.domain.HomeRepository

class HomeRepositoryImpl(
    private val wifiService: WifiService
): HomeRepository {
    override fun startWifiTracking(onSsidDetected: (String) -> Unit) {
        wifiService.startWifiTracking(onSsidDetected)
    }

    override fun stopWifiTracking() {
        wifiService.stopWifiTracking()
    }

    override fun openWifiSettings() {
        wifiService.openWifiSettings()
    }
    override fun getInitialSsid(): String? {
        return wifiService.getInitialSsid()
    }

}