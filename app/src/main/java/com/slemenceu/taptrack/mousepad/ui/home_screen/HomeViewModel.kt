package com.slemenceu.taptrack.mousepad.ui.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slemenceu.taptrack.authentication.data.AuthStatus
import com.slemenceu.taptrack.mousepad.data.services.WifiService
import com.slemenceu.taptrack.mousepad.domain.HomeRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authStatus: AuthStatus,
    private val repository: HomeRepository,
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<HomeUiEffect>()
    val uiEffect: SharedFlow<HomeUiEffect> = _uiEffect.asSharedFlow()

    fun onEvent(event: HomeUiEvent){
        when(event){
            is HomeUiEvent.onLogOutClicked -> {
                viewModelScope.launch {
                    logout()
                    sendEffect(HomeUiEffect.NavigateToLogin)
                }
            }
            HomeUiEvent.onOpenWifiSettings -> openWifiSettings()
            HomeUiEvent.startWifiTrackingEvent -> startTracking()
            HomeUiEvent.stopWifiTrackingEvent -> stopTracking()
            HomeUiEvent.loadInitialWifiInfo -> loadInitialWifiInfo()
            is HomeUiEvent.onPermissionResult -> onPermissionResult(event.result)
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

        val all = wifi && location
        _uiState.value = _uiState.value.copy(
            permissions = PermissionUiState(
                allPermissionGranted = all,
                isLocationGranted = location,
                isWifiStateGranted = wifi
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


}