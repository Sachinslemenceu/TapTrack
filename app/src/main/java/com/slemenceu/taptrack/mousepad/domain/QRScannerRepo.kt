package com.slemenceu.taptrack.mousepad.domain

import android.app.Activity
import android.content.Intent

interface QRScannerRepo {
    fun launchScanner(activity: Activity): Intent
}