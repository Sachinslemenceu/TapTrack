package com.slemenceu.taptrack.features.mousepad.domain

import android.app.Activity
import android.content.Intent

interface QRScannerRepo {
    fun launchScanner(activity: Activity): Intent
}