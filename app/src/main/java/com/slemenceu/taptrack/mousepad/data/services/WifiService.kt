package com.slemenceu.taptrack.mousepad.data.services

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.provider.Settings
import android.util.Log

class WifiService(
    private val context: Context
) {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var callback: ConnectivityManager.NetworkCallback? = null

    fun startWifiTracking(onSsidDetected: (String) -> Unit){
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
        callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val ssid = wifiManager.connectionInfo?.ssid?.replace("\"", "")
                Log.d("WifiService", "onAvailable:, Parsed SSID = $ssid")

                if (!ssid.isNullOrEmpty() && ssid != "<unknown ssid>") {
                    onSsidDetected(ssid)
                } else {
                    Log.d("WifiService", "SSID is empty or unknown")

                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                onSsidDetected("Disconnected")

            }

        }
        connectivityManager.registerNetworkCallback(networkRequest, callback!!)

    }
    fun stopWifiTracking(){
        callback?.let {
            connectivityManager.unregisterNetworkCallback(it)
        }
    }
    fun openWifiSettings(){
        val indent = Intent(Settings.ACTION_WIFI_SETTINGS)
        indent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(indent)
    }
    fun getInitialSsid(): String? {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val ssid = wifiManager.connectionInfo?.ssid?.replace("\"", "")
        return if (!ssid.isNullOrEmpty() && ssid != "<unknown ssid>") ssid else null
    }
}