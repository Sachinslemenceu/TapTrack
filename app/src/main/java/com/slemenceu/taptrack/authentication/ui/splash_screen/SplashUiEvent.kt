package com.slemenceu.taptrack.authentication.ui.splash_screen

sealed class SplashUiEvent {
    object OnGetStartedClicked: SplashUiEvent()
    object OnAnimationDone: SplashUiEvent()
    object OnUserLoggedIn: SplashUiEvent()
}