package com.slemenceu.taptrack.features.authentication.ui.register_screen

sealed class RegisterUiEffect {
    object NavigateToHome : RegisterUiEffect()
    object PasswordUnmatched : RegisterUiEffect()
}