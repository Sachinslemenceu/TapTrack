package com.slemenceu.taptrack.authentication.ui.splash_screen

sealed class SplashUiEffect {
    object NavigateToLogin : SplashUiEffect()
    object NavigateToHome : SplashUiEffect()
}