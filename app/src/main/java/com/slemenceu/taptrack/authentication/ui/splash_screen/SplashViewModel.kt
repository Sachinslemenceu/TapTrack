package com.slemenceu.taptrack.authentication.ui.splash_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slemenceu.taptrack.authentication.data.AuthStatus
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SplashViewModel(
    val authStatusRepo: AuthStatus
): ViewModel() {
    val authStatus: StateFlow<Boolean> = authStatusRepo.readAuthStatus()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)
    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<SplashUiEffect>()
    val uiEffect: SharedFlow<SplashUiEffect> = _uiEffect.asSharedFlow()

    fun onEvent(event: SplashUiEvent){
        when(event){
            is SplashUiEvent.OnGetStartedClicked -> {
                viewModelScope.launch {
                    sendEffect(SplashUiEffect.NavigateToLogin)
                }
            }
            is SplashUiEvent.OnAnimationDone -> {
                _uiState.value = _uiState.value.copy(isAnimationFinished = true)
            }

            SplashUiEvent.OnUserLoggedIn -> {
                viewModelScope.launch {
                    sendEffect(SplashUiEffect.NavigateToHome)
                }
            }
        }
    }

    private suspend fun sendEffect(effect: SplashUiEffect) {
        _uiEffect.emit(effect)
    }
}