package com.slemenceu.taptrack.features.connection.data.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build



/**
 * Returns the name (SSID) of the currently connected network.
 *
 * Note: To get the actual SSID on Android 10+, you MUST have:
 * 1. ACCESS_FINE_LOCATION permission granted.
 * 2. Location services enabled on the device.
 */
fun getConnectedWifiName(context: Context): String {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return "Disconnected"
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return "Disconnected"

    return when {
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
            // Modern way to get WifiInfo (Android 12+)
            val wifiInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                capabilities.transportInfo as? WifiInfo
            } else {
                // Fallback for older versions
                val wifiManager =
                    context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                wifiManager.connectionInfo
            }

            val ssid = wifiInfo?.ssid?.replace("\"", "")

            if (ssid == null || ssid == "<unknown ssid>" || ssid.isEmpty()) {
                "WiFi Connected" // Fallback name when permissions/location are missing
            } else {
                ssid
            }
        }

        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Cellular Network"
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "Ethernet"
        else -> "Connected"
    }
}