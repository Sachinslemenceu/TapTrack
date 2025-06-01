package com.slemenceu.taptrack.authentication.ui.register_screen

sealed class RegisterUiEffect {
    object NavigateToHome : RegisterUiEffect()
    object PasswordUnmatched : RegisterUiEffect()
}