package com.slemenceu.taptrack.mousepad.ui.home_screen

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import com.slemenceu.taptrack.authentication.data.AuthStatus
import com.slemenceu.taptrack.mousepad.domain.HomeRepository
import com.slemenceu.taptrack.mousepad.domain.MouseRepository
import com.slemenceu.taptrack.mousepad.domain.QRScannerRepo
import com.slemenceu.taptrack.mousepad.ui.home_screen.HomeUiEffect.*
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authStatus: AuthStatus,
    private val repository: HomeRepository,
    private val mouseRepository: MouseRepository,
    private val qrScannerRepo: QRScannerRepo
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

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

    private val _uiEffect = MutableSharedFlow<HomeUiEffect>()
    val uiEffect: SharedFlow<HomeUiEffect> = _uiEffect.asSharedFlow()

    fun onEvent(event: HomeUiEvent){
        when(event){
            HomeUiEvent.onOpenWifiSettings -> openWifiSettings()
            HomeUiEvent.startWifiTrackingEvent -> startTracking()
            HomeUiEvent.stopWifiTrackingEvent -> stopTracking()
            HomeUiEvent.loadInitialWifiInfo -> loadInitialWifiInfo()
            is HomeUiEvent.onPermissionResult -> onPermissionResult(event.result)
            HomeUiEvent.onNavigateToMousepad -> {
                viewModelScope.launch {
                    sendEffect(NavigateToMousepad)
                }
            }

            is HomeUiEvent.onOpenScanner -> viewModelScope.launch {
                sendEffect(onQrScanClicked(scannerIntent(event.activity)))
            }
            is HomeUiEvent.onScannedResult -> {
                Log.d("HomeScreenLog", "onScannedResult: ${event.result}")
                scannedResult(event.result)
            }

            HomeUiEvent.onScanCancelled -> {
                viewModelScope.launch {
                    sendEffect(onQrScanCancelled)
                }
            }
            HomeUiEvent.onPcGuideClicked -> {
                viewModelScope.launch {
                    sendEffect(NavigateToPcGuide)
                }
            }

            HomeUiEvent.onOptionsClicked -> {
                viewModelScope.launch {
                    sendEffect(NavigateToOptions)
                }
            }
        }
    }

    private suspend fun sendEffect(effect: HomeUiEffect){
        _uiEffect.emit(effect)
    }
    private suspend fun logout(){
        authStatus.saveAuthStatus(false)
    }

    private fun onPermissionResult(result: Map<String, Boolean>){
        val wifi = result[android.Manifest.permission.ACCESS_WIFI_STATE] ?: false
        val location = result[android.Manifest.permission.ACCESS_COARSE_LOCATION] ?: false &&
                result[android.Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val camera = result[android.Manifest.permission.CAMERA] ?: false
        val all = wifi && location && camera
        _uiState.value = _uiState.value.copy(
            permissions = PermissionUiState(
                allPermissionGranted = all,
                isLocationGranted = location,
                isWifiStateGranted = wifi,
                isCameraGranted = camera
            )
        )
    }
    private fun startTracking() {
        repository.startWifiTracking { ssid ->
            val isConnected = ssid != "Disconnected"
            _uiState.value = _uiState.value.copy(
                ssid = ssid,
                isConnected = isConnected
            )
        }
    }
    private suspend fun connectToMousepad(passcode: Int): Boolean {
        Log.d("HomeScreenLog", "connecting to mousepad: $passcode")
        val result = mouseRepository.connectToMousepad(passcode)
        Log.d("HomeScreenLog", "result of connection: $result")
        return result
    }

    private fun stopTracking() {
        repository.stopWifiTracking()
    }

    private fun openWifiSettings() {
        repository.openWifiSettings()
    }

    override fun onCleared() {
        super.onCleared()
        stopTracking()
    }
    private fun loadInitialWifiInfo() {
        val currentSsid = repository.getInitialSsid()
        val isConnected = currentSsid != null && currentSsid != "Disconnected"

        _uiState.value = _uiState.value.copy(
            ssid = currentSsid ?: "Not connected",
            isConnected = isConnected
        )
    }

    private fun scannerIntent(activity: Activity): Intent {
        return qrScannerRepo.launchScanner(activity)
    }
    private fun scannedResult(result: String){
        viewModelScope.launch {
            val connected = connectToMousepad(result.toInt())
            _uiState.value = _uiState.value.copy(
                mousepad = _uiState.value.mousepad.copy(
                    isConnected = connected
                )
            )
        }


    }
}