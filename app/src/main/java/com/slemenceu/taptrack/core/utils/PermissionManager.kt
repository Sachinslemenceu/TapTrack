package com.slemenceu.taptrack.core.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object PermissionManager{
    fun isLocationPermissionGranted(context: Context): Boolean{
         return ContextCompat.checkSelfPermission(
             context,
             android.Manifest.permission.ACCESS_COARSE_LOCATION
         ) == PackageManager.PERMISSION_GRANTED &&
                 ContextCompat.checkSelfPermission(
                     context,
                     android.Manifest.permission.ACCESS_FINE_LOCATION
                 ) == PackageManager.PERMISSION_GRANTED
    }

    fun isWifiStatePermissionGranted(context: Context): Boolean{
        return ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_WIFI_STATE
        ) == PackageManager.PERMISSION_GRANTED
    }
    fun isAllPermissionGranted(context: Context): Boolean{
        return isLocationPermissionGranted(context) && isWifiStatePermissionGranted(context)
    }
}