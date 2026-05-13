package com.slemenceu.taptrack.features.authentication.ui.splash_screen

sealed class SplashUiEffect {
    object NavigateToLogin : SplashUiEffect()
    object NavigateToHome : SplashUiEffect()
}