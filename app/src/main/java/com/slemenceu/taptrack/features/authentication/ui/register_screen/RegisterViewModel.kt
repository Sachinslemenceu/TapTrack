package com.slemenceu.taptrack.features.authentication.ui.register_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slemenceu.taptrack.features.authentication.domain.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepo: AuthRepository,
) : ViewModel() {

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

            is RegisterUiEvent.OnNameChanged -> {
                _uiState.value = _uiState.value.copy(name = event.name)
            }

            is RegisterUiEvent.OnPasswordChanged -> {
                _uiState.value = _uiState.value.copy(password = event.password)
            }

            RegisterUiEvent.OnRegisterClicked -> {
                _uiState.value = _uiState.value.copy(isLoading = true)

                viewModelScope.launch {
                    Log.d("RegisterViewModel", "Attempting to register with email: ${uiState.value.email}, name: ${uiState.value.name}")

                    val result = register(uiState.value.name,uiState.value.email, uiState.value.password)
                    Log.d("RegisterViewModel", "Registration result: $result")
                    if (result) {
                        authRepo.saveAuthStatus(isLoggedIn = true)
                        authRepo.saveFirstLoginStatus(true)
                        emitEffect(RegisterUiEffect.NavigateToHome)
                    } else {
                        emitEffect(RegisterUiEffect.PasswordUnmatched)
                        clearFields()
                    }
                }
            }
        }
    }

    private suspend fun register(name: String,email: String, password: String): Boolean {
        val result = authRepo.register(name,email, password)
        _uiState.value = _uiState.value.copy(isLoading = false)
        return result
    }

    private fun clearFields() {
        _uiState.value = _uiState.value.copy(
            email = "",
            password = "",
            name = ""
        )
    }

}