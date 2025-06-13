package com.slemenceu.taptrack.mousepad.ui.mousepad_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slemenceu.taptrack.mousepad.domain.MouseRepository
import kotlinx.coroutines.launch

class MouseViewModel(
    private val repository: MouseRepository,
): ViewModel() {
    fun onEvent(event: MouseUiEvent) {
        when (event) {
            is MouseUiEvent.SendClick -> {
                viewModelScope.launch {
                    repository.sendClick(event.rightClick)
                }
            }
            is MouseUiEvent.SendMouseMove -> {
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
            repository.disconnectFromMousePad()
        }
    }
}