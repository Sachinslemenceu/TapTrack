package com.slemenceu.taptrack.mousepad.ui.home_screen

sealed class HomeUiEffect {
    object NavigateToLogin : HomeUiEffect()
    object NavigateToMousepad : HomeUiEffect()
}