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
import androidx.camera.core.TorchState
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.slemenceu.taptrack.features.connection.domain.usecases.ConnectToPcUseCase
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScannerViewModel(
    private val connect: ConnectToPcUseCase
): ViewModel() {

    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
    val surfaceRequest = _surfaceRequest.asStateFlow()

    private val cameraPreviewUseCase = Preview.Builder()
        .build().apply {
            setSurfaceProvider { newSurfaceRequest ->
                _surfaceRequest.update { newSurfaceRequest }
            }
        }

    private val _isFlashOn = MutableStateFlow(false)
    val isFlashOn = _isFlashOn.asStateFlow()


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
        camera?.cameraInfo?.torchState?.observe(lifecycleOwner) { torchState ->
            _isFlashOn.value = torchState == TorchState.ON
        }
        try {
            awaitCancellation()
        } finally {
            processCameraProvider?.unbindAll()
        }
    }
    fun extractQrCodeFromImage(
        image: InputImage,
        onQrCodeScanned: (String) -> Unit
    ){
        processImage(barcodeScanner, image, onQrCodeScanned)
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

    private fun processImage(
        barcodeScanner: BarcodeScanner,
        inputImage: InputImage,
        onQrCodeScanned: (String) -> Unit
    ){
        barcodeScanner.process(inputImage)
            .addOnSuccessListener { barcodes ->
                if (barcodes.isEmpty()) {
                    Log.d("ImageExtract", "No QR codes found")
                    onQrCodeScanned("")
                }
                for (barcode in barcodes) {
                    barcode.rawValue?.let { value ->
                        onQrCodeScanned(value)
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e("ImageExtract", "Error scanning QR: ${e.message}")
            }
    }

    fun turnFlashLightOnAndOff(){
        if (camera?.cameraInfo?.hasFlashUnit() == true) {
            camera?.cameraControl?.enableTorch(!_isFlashOn.value)
        }
    }

    fun connectToPcWithQrCode(qrCode: String, onResult: (Boolean) -> Unit){
        viewModelScope.launch {
            connect(qrCode).onSuccess {
                onResult(true)
            }.onFailure {
                onResult(false)
            }
        }
    }
}