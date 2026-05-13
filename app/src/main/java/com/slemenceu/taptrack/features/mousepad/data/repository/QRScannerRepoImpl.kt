package com.slemenceu.taptrack.features.mousepad.data.repository

import android.app.Activity
import android.content.Intent
import com.google.zxing.integration.android.IntentIntegrator
import com.slemenceu.taptrack.CustomCaptureActivity
import com.slemenceu.taptrack.features.mousepad.domain.QRScannerRepo

class QRScannerRepoImpl: QRScannerRepo {
    override fun launchScanner(activity: Activity): Intent {
        val integrator = IntentIntegrator(activity)
        integrator.setBeepEnabled(true)
        integrator.setPrompt("Scan a QR Code")
        integrator.captureActivity = CustomCaptureActivity::class.java
        val intent = integrator.createScanIntent()
        return intent
    }
}