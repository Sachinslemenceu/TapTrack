package com.slemenceu.taptrack.authentication.ui.register_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slemenceu.taptrack.authentication.data.AuthService
import com.slemenceu.taptrack.authentication.data.AuthStatus
import com.slemenceu.taptrack.authentication.domain.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepo: AuthRepository,
): ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()
    private val _uiEffect = MutableSharedFlow<RegisterUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private suspend fun emitEffect(effect: RegisterUiEffect) {
        _uiEffect.emit(effect)
    }

    fun onEvent(event: RegisterUiEvent) {
        when (event) {
            is RegisterUiEvent.OnEmailChanged -> {
                _uiState.value = _uiState.value.copy(email = event.email)
            }

            is RegisterUiEvent.OnConfirmPasswordChanged -> {
                _uiState.value = _uiState.value.copy(confirmPassword = event.confirmPassword)
            }
            is RegisterUiEvent.OnPasswordChanged -> {
                _uiState.value = _uiState.value.copy(password = event.password)
            }
            RegisterUiEvent.OnRegisterClicked -> {
                _uiState.value = _uiState.value.copy(isLoading = true)

                viewModelScope.launch {
                    if (uiState.value.password != uiState.value.confirmPassword) {
                        emitEffect(RegisterUiEffect.PasswordUnmatched)
                        clearFields()
                    } else {
                        val result = register(uiState.value.email, uiState.value.password)
                        if (result) {
                            authRepo.saveAuthStatus(isLoggedIn = true)
                            emitEffect(RegisterUiEffect.NavigateToHome)
                        } else {
                            emitEffect(RegisterUiEffect.PasswordUnmatched)
                            clearFields()
                        }
                    }
                }
            }
        }
    }
    private suspend fun register(email: String, password: String): Boolean {
        val result = authRepo.register(email, password)
        _uiState.value = _uiState.value.copy(isLoading = false)
        return result
    }
    private fun clearFields() {
        _uiState.value = _uiState.value.copy(
            email = "",
            password = "",
            confirmPassword = ""
        )
    }

}