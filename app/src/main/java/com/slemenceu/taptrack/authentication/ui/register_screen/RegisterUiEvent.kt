package com.slemenceu.taptrack.authentication.ui.register_screen

sealed class RegisterUiEvent {
    data class OnEmailChanged(val email: String) : RegisterUiEvent()
    data class OnPasswordChanged(val password: String) : RegisterUiEvent()
    data class OnConfirmPasswordChanged(val confirmPassword: String) : RegisterUiEvent()
    object OnRegisterClicked : RegisterUiEvent()
}