package com.slemenceu.taptrack.features.mousepad.ui.settings_screen.security

data class SecurityUiState(
    val newPassword: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)

sealed interface SecurityUiEvent {
    data class OnNewPasswordChanged(val password: String) : SecurityUiEvent
    data class OnConfirmPasswordChanged(val password: String) : SecurityUiEvent
    object OnUpdatePasswordClicked : SecurityUiEvent
    object DismissMessages : SecurityUiEvent
}

sealed interface SecurityUiEffect {
    object NavigateBack : SecurityUiEffect
    data class ShowToast(val message: String) : SecurityUiEffect
}
