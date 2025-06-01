package com.slemenceu.taptrack.authentication.ui.login_screen

sealed class LoginUiEffect {
    object NavigateToHome : LoginUiEffect()
    object NavigateToRegister : LoginUiEffect()
    object InvalidCredential: LoginUiEffect()
}