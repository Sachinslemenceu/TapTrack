package com.slemenceu.taptrack.authentication.ui.login_screen

sealed class LoginUiEvent {
    data class OnEmailChanged(val email: String) : LoginUiEvent()
    data class OnPasswordChanged(val password: String) : LoginUiEvent()
    object OnLoginClicked : LoginUiEvent()
    object OnRegisterClicked : LoginUiEvent()
}