package com.slemenceu.taptrack.features.connection.ui.scanner

import android.content.Context
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.slemenceu.taptrack.features.connection.data.utils.getConnectedWifiName
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ScannerViewModel(
): ViewModel() {

    private val _uiState = MutableStateFlow(ScannerScreenUiState())
    val uiState = _uiState.asStateFlow()
    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
    val surfaceRequest = _surfaceRequest.asStateFlow()

    private val cameraPreviewUseCase = Preview.Builder()
        .build().apply {
            setSurfaceProvider { newSurfaceRequest ->
                _surfaceRequest.update { newSurfaceRequest }
            }
        }




    private val barcodeScanner = BarcodeScanning.getClient()

    private var processCameraProvider: ProcessCameraProvider? = null
    private var camera: Camera? = null

    suspend fun bindToCamera(
        appContext: Context,
        lifecycleOwner: LifecycleOwner,
        onQrCodeScanned: (barcode: String) -> Unit
    ) {
        processCameraProvider = ProcessCameraProvider.awaitInstance(appContext)
        val analysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .apply {
                setAnalyzer(
                    ContextCompat.getMainExecutor(appContext),
                    { imageProxy ->
                        processImageProxy(barcodeScanner, imageProxy, onQrCodeScanned)
                    }
                )
            }
        camera = processCameraProvider?.bindToLifecycle(
            lifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, cameraPreviewUseCase, analysis
        )
        try {
            awaitCancellation()
        } finally {
            processCameraProvider?.unbindAll()
        }
    }

    @OptIn(ExperimentalGetImage::class)
    private fun processImageProxy(
        barcodeScanner: BarcodeScanner,
        imageProxy: ImageProxy,
        onQrCodeScanned: (String) -> Unit
    ) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            barcodeScanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        barcode.rawValue?.let { value ->
                            onQrCodeScanned(value)
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("QR", "Error scanning QR: ${e.message}")
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }


    fun fetchConnectedNetworkInfo(context: Context){
        val wifiName = getConnectedWifiName(context)
        _uiState.update { it.copy(connectedNetworkName = wifiName) }
    }

}