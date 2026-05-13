package com.slemenceu.taptrack.features.authentication.ui.splash_screen

sealed class SplashUiEvent {
    object OnAnimationDone: SplashUiEvent()
    object OnUserLoggedIn: SplashUiEvent()
}