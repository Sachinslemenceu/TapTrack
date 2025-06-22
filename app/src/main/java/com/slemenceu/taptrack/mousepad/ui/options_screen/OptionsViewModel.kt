package com.slemenceu.taptrack.mousepad.ui.options_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slemenceu.taptrack.authentication.data.AuthStatus
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class OptionsViewModel(
    private val authStatus: AuthStatus
): ViewModel() {
    private val _uiEffect = MutableSharedFlow<OptionsUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun onEvent(event: OptionsUiEvent) {
        when (event) {
            OptionsUiEvent.OnBackClicked -> {
                viewModelScope.launch {
                    sendEffect(OptionsUiEffect.NavigateToHome)
                }
            }
            OptionsUiEvent.OnLogoutClicked -> {
                viewModelScope.launch {
                    authStatus.saveAuthStatus(
                        isLoggedIn = false
                    )
                    sendEffect(OptionsUiEffect.NavigateToLogin)
                }
            }
            OptionsUiEvent.OnUnfinishedFeatureClicked -> {
                viewModelScope.launch {
                    sendEffect(OptionsUiEffect.ShowToast)
                }
            }
        }
    }

    private suspend fun sendEffect(effect: OptionsUiEffect) {
        _uiEffect.emit(effect)
    }
}