package com.slemenceu.taptrack.features.mousepad.ui.trackpad_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slemenceu.taptrack.features.connection.domain.usecases.DisconnectUseCase
import com.slemenceu.taptrack.features.connection.domain.usecases.GetConnectionStatusUseCase
import com.slemenceu.taptrack.features.connection.domain.usecases.GetLatencyUseCase
import com.slemenceu.taptrack.features.mousepad.domain.MouseRepository
import kotlinx.coroutines.launch

class TrackpadViewModel(
    private val repository: MouseRepository,
    private val getConnectionStatus: GetConnectionStatusUseCase,
    private val getLatency: GetLatencyUseCase,
    private val disconnect: DisconnectUseCase
): ViewModel() {


    init {
        viewModelScope.launch {
            getConnectionStatus().collect{
                Log.d("TrackpadViewModel", "Connection status: $it")
            }
        }
        viewModelScope.launch {
            getLatency().collect {
                Log.d("TrackpadViewModel", "Latency: $it")
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
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            repository.disconnect()
        }
    }
}