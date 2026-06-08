package com.slemenceu.taptrack.features.mousepad.ui.home_screen

import android.Manifest
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slemenceu.taptrack.features.authentication.data.AuthStatus
import com.slemenceu.taptrack.features.authentication.domain.AuthRepository
import com.slemenceu.taptrack.features.connection.domain.usecases.ConnectToPcUseCase
import com.slemenceu.taptrack.features.connection.domain.usecases.GetConnectionStatusUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val connectToPc: ConnectToPcUseCase,
    private val getConnectionStatus: GetConnectionStatusUseCase,
    private val authStatus: AuthStatus,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val TAG = "HomeViewModel"
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()


    private val _uiEffect = MutableSharedFlow<HomeUiEffect>()
    val uiEffect: SharedFlow<HomeUiEffect> = _uiEffect.asSharedFlow()


    init {
        viewModelScope.launch {
            authStatus.readFirstLoginStatus().collect { hasLoggedInBefore ->
                _uiState.update {
                    it.copy(isFirstTime = !hasLoggedInBefore)
                }
                Log.d(TAG,"Is first time: ${uiState.value.isFirstTime}, hasLoggedInBefore: $hasLoggedInBefore")
            }

        }
        viewModelScope.launch {
            getConnectionStatus().collect { connectionStatus ->
                Log.d(TAG, "The Connection Status is : $connectionStatus")

                _uiState.update {
                    it.copy(connectionStatus = connectionStatus)
                }
            }
        }
        viewModelScope.launch {
            authRepository.readUserName().collect{name->
                _uiState.update {
                    it.copy(userName = name)
                }
            }
        }
    }


    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.onPermissionResult -> onPermissionResult(event.result)
            HomeUiEvent.onNavigateToMousepad -> {
                viewModelScope.launch {
                    sendEffect(HomeUiEffect.NavigateToMousepad)
                }
            }

            is HomeUiEvent.onOpenScanner -> viewModelScope.launch {
            }

            is HomeUiEvent.onScannedResult -> {
                Log.d("HomeScreenLog", "onScannedResult: ${event.result}")
            }

            HomeUiEvent.onScanCancelled -> {
                viewModelScope.launch {
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

            is HomeUiEvent.Connect -> {
                viewModelScope.launch {
                    connectToPc(event.connectionInfo)
                        .onSuccess {connectionResult->
                            Log.d(TAG,"The latency is ${connectionResult.latency}")
                            _uiState.update {
                                it.copy(latency = connectionResult.latency, deviceName = connectionResult.deviceName, networkName = connectionResult.networkName, isFirstTime = false)
                            }
                        }
                        .onFailure {
                            Log.d(TAG, "The error is $it")
//                            _uiState.update { it.copy(connectionStatus = ConnectionStatus.Disconnected) }
                        }
                }
            }
        }
    }

    private suspend fun sendEffect(effect: HomeUiEffect) {
        _uiEffect.emit(effect)
    }

    private suspend fun logout() {
        authStatus.saveAuthStatus(false)
    }

    private fun onPermissionResult(result: Map<String, Boolean>) {
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


    override fun onCleared() {
        super.onCleared()
    }

}