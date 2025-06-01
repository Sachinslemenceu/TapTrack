package com.slemenceu.taptrack.authentication.ui.login_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slemenceu.taptrack.authentication.data.AuthService
import com.slemenceu.taptrack.authentication.data.AuthStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authService: AuthService,
    private val authStatus: AuthStatus
): ViewModel() {


    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<LoginUiEffect>()
    val uiEffect: SharedFlow<LoginUiEffect> = _uiEffect.asSharedFlow()

    fun onEvent(event: LoginUiEvent){
        when(event){
            is LoginUiEvent.OnEmailChanged -> {
                _uiState.value = _uiState.value.copy(email = event.email)
            }
            is LoginUiEvent.OnPasswordChanged -> {
                _uiState.value = _uiState.value.copy(password = event.password)
            }
            is LoginUiEvent.OnLoginClicked -> {
                _uiState.value = _uiState.value.copy(isLoading = true)
                viewModelScope.launch {
                    val result = validateCredentials(uiState.value.email, uiState.value.password)
                    if(result){
                        authStatus.saveAuthStatus(isLoggedIn = true)
                        sendEffect(LoginUiEffect.NavigateToHome)
                    }else{
                        sendEffect(LoginUiEffect.InvalidCredential)
                        clearLoginCredential()
                    }
                }
            }
            is LoginUiEvent.OnRegisterClicked -> {
                viewModelScope.launch {
                    Log.d("LoginViewModel", "Register effect send")
                    sendEffect(LoginUiEffect.NavigateToRegister)
                }
            }
        }
    }

    private suspend fun sendEffect(effect: LoginUiEffect){
        _uiEffect.emit(effect)
    }

    private suspend fun validateCredentials(email: String, password: String): Boolean{
        val result = authService.login(email, password)
        _uiState.value = _uiState.value.copy(isLoading = false)
        return result
    }
    private fun clearLoginCredential(){
        _uiState.value = _uiState.value.copy(email = "", password = "")
    }

}