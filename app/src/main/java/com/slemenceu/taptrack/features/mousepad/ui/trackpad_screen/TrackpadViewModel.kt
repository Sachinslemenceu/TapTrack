package com.slemenceu.taptrack.features.mousepad.ui.trackpad_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slemenceu.taptrack.features.connection.domain.models.ConnectionStatus
import com.slemenceu.taptrack.features.connection.domain.usecases.DisconnectUseCase
import com.slemenceu.taptrack.features.connection.domain.usecases.GetConnectionStatusUseCase
import com.slemenceu.taptrack.features.connection.domain.usecases.GetLatencyUseCase
import com.slemenceu.taptrack.features.mousepad.domain.MouseRepository
import com.slemenceu.taptrack.features.mousepad.ui.home_screen.HomeUiEffect
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrackpadViewModel(
    private val repository: MouseRepository,
    private val getConnectionStatus: GetConnectionStatusUseCase,
    private val getLatency: GetLatencyUseCase,
    private val disconnect: DisconnectUseCase
): ViewModel() {

    private val _uiEffect = MutableSharedFlow<TrackpadUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private val _uiState = MutableStateFlow(TrackPadUiState())
    val uiState = _uiState.asStateFlow()

    var ignoredFirstDisconnect = false


    init {
        viewModelScope.launch {
            getConnectionStatus().collect{
                Log.d("TrackpadViewModel", "Connection status: $it")
                _uiState.value = _uiState.value.copy(connectionStatus = it)
                if (it == ConnectionStatus.Disconnected) {
                    if (ignoredFirstDisconnect) sendEffect(TrackpadUiEffect.ConnectionLost)
                    ignoredFirstDisconnect = true
                }
            }
        }
        viewModelScope.launch {
            getLatency().collect {latency->
                latency?.let {
                    _uiState.value = _uiState.value.copy(latency = it)
                }
                Log.d("TrackpadViewModel", "Latency: $latency")
            }
        }
    }
    fun onEvent(event: TrackpadUiEvent) {
        when (event) {
            is TrackpadUiEvent.SendClick -> {
                viewModelScope.launch {
                    repository.sendClick(event.rightClick)
                }
            }
            is TrackpadUiEvent.SendTrackpadMove -> {
                viewModelScope.launch {
                    repository.sendMouseMove(event.dx, event.dy)
                    Log.d("MouseViewModel", "Mouse move sent: dx=${event.dx}, dy=${event.dy}")
                }
            }

            TrackpadUiEvent.OnDisconnect -> {
                viewModelScope.launch {
                    disconnect().onSuccess {
                        sendEffect(TrackpadUiEffect.NavigateToHome)
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            repository.disconnect()
        }
    }

    private suspend fun sendEffect(effect: TrackpadUiEffect) {
        _uiEffect.emit(effect)
    }
}