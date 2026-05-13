package com.slemenceu.taptrack.features.mousepad.ui.home_screen

import android.Manifest
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
import com.slemenceu.taptrack.features.authentication.data.AuthStatus
import com.slemenceu.taptrack.features.mousepad.domain.HomeRepository
import com.slemenceu.taptrack.features.mousepad.domain.MouseRepository
import com.slemenceu.taptrack.features.mousepad.domain.QRScannerRepo
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
                    sendEffect(HomeUiEffect.NavigateToMousepad)
                }
            }

            is HomeUiEvent.onOpenScanner -> viewModelScope.launch {
                sendEffect(HomeUiEffect.onQrScanClicked(scannerIntent(event.activity)))
            }
            is HomeUiEvent.onScannedResult -> {
                Log.d("HomeScreenLog", "onScannedResult: ${event.result}")
                scannedResult(event.result)
            }

            HomeUiEvent.onScanCancelled -> {
                viewModelScope.launch {
                    sendEffect(HomeUiEffect.onQrScanCancelled)
                }
            }
            HomeUiEvent.onPcGuideClicked -> {
                viewModelScope.launch {
                    sendEffect(HomeUiEffect.NavigateToPcGuide)
                }
            }

            HomeUiEvent.onOptionsClicked -> {
                viewModelScope.launch {
                    sendEffect(HomeUiEffect.NavigateToOptions)
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
        val wifi = result[Manifest.permission.ACCESS_WIFI_STATE] ?: false
        val location = result[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false &&
                result[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val camera = result[Manifest.permission.CAMERA] ?: false
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
        Log.d("HomeScreenLog", "scanned result: $result")
        viewModelScope.launch {
            try {
                // Parse QR format: "IP:PORT:PASSCODE" (e.g., "192.168.1.4:9999:1234")
                val parts = result.split(":")
                
                if (parts.size == 3) {
                    // New format with IP and Port from QR code
                    val ip = parts[0]
                    val port = parts[1].toInt()
                    val passcode = parts[2].toInt()
                    
                    Log.d("HomeScreenLog", "Parsed QR - IP: $ip, Port: $port, Passcode: $passcode")
                    
                    // Connect with config extracted from QR
                    val connected = mouseRepository.connectToMousepadWithConfig(ip, port, passcode)
                    _uiState.value = _uiState.value.copy(
                        mousepad = _uiState.value.mousepad.copy(
                            isConnected = connected
                        )
                    )
                } else if (parts.size == 1) {
                    // Fallback: try parsing as just passcode for backward compatibility
                    Log.d("HomeScreenLog", "Fallback: parsing as passcode only (old format)")
                    val connected = mouseRepository.connectToMousepad(result.toInt())
                    _uiState.value = _uiState.value.copy(
                        mousepad = _uiState.value.mousepad.copy(
                            isConnected = connected
                        )
                    )
                } else {
                    Log.e("HomeScreenLog", "Invalid QR format. Expected IP:PORT:PASSCODE or just PASSCODE")
                }
            } catch (e: Exception) {
                Log.e("HomeScreenLog", "Error parsing QR result: ${e.message}", e)
            }
        }

    }
}