package com.slemenceu.taptrack.features.authentication.ui.login_screen

sealed class LoginUiEffect {
    object NavigateToHome : LoginUiEffect()
    object InvalidCredential: LoginUiEffect()
}