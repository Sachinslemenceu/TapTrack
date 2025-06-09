package com.slemenceu.taptrack.mousepad.data.repository

import android.content.Context
import android.net.wifi.WifiManager
import com.slemenceu.taptrack.core.utils.PermissionManager
import com.slemenceu.taptrack.mousepad.data.services.WifiService
import com.slemenceu.taptrack.mousepad.domain.HomeRepository

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