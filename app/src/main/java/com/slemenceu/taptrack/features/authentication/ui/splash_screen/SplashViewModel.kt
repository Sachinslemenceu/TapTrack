package com.slemenceu.taptrack.features.authentication.ui.splash_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slemenceu.taptrack.features.authentication.domain.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SplashViewModel(
    private val authRepo: AuthRepository,
): ViewModel() {

    private val isLoggedIn = MutableStateFlow<Boolean?>(null)
    init {
        checkAuthStatus()
    }
    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<SplashUiEffect>()
    val uiEffect: SharedFlow<SplashUiEffect> = _uiEffect.asSharedFlow()

    fun onEvent(event: SplashUiEvent){
        when(event){

            is SplashUiEvent.OnAnimationDone -> {
                viewModelScope.launch {
                    if (isLoggedIn.value == true) {
//                        authRepo.saveFirstLoginStatus(true)
                        sendEffect(SplashUiEffect.NavigateToHome)
                    } else {
                        _uiState.value = _uiState.value.copy(isAnimationFinished = true)
                    }
                }
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
    private fun checkAuthStatus(){
        viewModelScope.launch {
            authRepo.readAuthStatus().collectLatest{
                isLoggedIn.value = it
            }
        }
    }
}