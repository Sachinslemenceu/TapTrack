package com.slemenceu.taptrack.features.mousepad.ui.settings_screen.security

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slemenceu.taptrack.features.authentication.domain.usecase.UpdatePasswordUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SecurityViewModel(
    private val updatePasswordUseCase: UpdatePasswordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SecurityUiState())
    val uiState: StateFlow<SecurityUiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<SecurityUiEffect>()
    val uiEffect: SharedFlow<SecurityUiEffect> = _uiEffect.asSharedFlow()

    fun onEvent(event: SecurityUiEvent) {
        when (event) {
            is SecurityUiEvent.OnNewPasswordChanged -> {
                _uiState.update { it.copy(newPassword = event.password) }
            }
            is SecurityUiEvent.OnConfirmPasswordChanged -> {
                _uiState.update { it.copy(confirmPassword = event.password) }
            }
            SecurityUiEvent.OnUpdatePasswordClicked -> {
                updatePassword()
            }
            SecurityUiEvent.DismissMessages -> {
                _uiState.update { it.copy(errorMessage = null, isSuccess = false) }
            }
        }
    }

    private fun updatePassword() {
        val state = _uiState.value
        if (state.newPassword.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Password cannot be empty") }
            return
        }
        if (state.newPassword != state.confirmPassword) {
            _uiState.update { it.copy(errorMessage = "Passwords do not match") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val success = updatePasswordUseCase(state.newPassword)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    isSuccess = success,
                    errorMessage = if (success) null else "Failed to update password",
                    newPassword = if (success) "" else it.newPassword,
                    confirmPassword = if (success) "" else it.confirmPassword
                )
            }
            if (success) {
                _uiEffect.emit(SecurityUiEffect.ShowToast("Password updated successfully"))
            }
        }
    }
}
