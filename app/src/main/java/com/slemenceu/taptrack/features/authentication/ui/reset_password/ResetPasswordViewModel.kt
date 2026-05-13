package com.slemenceu.taptrack.features.authentication.ui.reset_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slemenceu.taptrack.features.authentication.domain.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val authRepo: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ResetPasswordUiState())
    val uiState = _uiState.asStateFlow()
    private val _uiEffect = MutableSharedFlow<ResetPasswordUiEffect>()
    val uiEffect: SharedFlow<ResetPasswordUiEffect> = _uiEffect.asSharedFlow()

    fun onEvent(event: ResetPasswordUiEvent) {
        when (event) {
            is ResetPasswordUiEvent.OnEmailChanged -> {
               _uiState.value = _uiState.value.copy(email = event.email)
            }

            is ResetPasswordUiEvent.OnSendResetLinkClicked -> {
                viewModelScope.launch {
                    val email = uiState.value.email
                    if (email.isBlank()) {
                        return@launch
                    }
                    val success = authRepo.sendResetPasswordLink(email)
                    if (success) {
                        sendEffect(ResetPasswordUiEffect.OnResetLinkSent)
                    } else {
                        sendEffect(ResetPasswordUiEffect.ShowToast("Failed to send reset link"))
                    }
                }
            }
        }
    }

    private suspend fun sendEffect(effect: ResetPasswordUiEffect) {
        _uiEffect.emit(effect)
    }

}