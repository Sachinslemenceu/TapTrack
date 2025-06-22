package com.slemenceu.taptrack.mousepad.ui.options_screen

sealed class OptionsUiEffect {
    object NavigateToLogin: OptionsUiEffect()
    object ShowToast: OptionsUiEffect()
    object NavigateToHome: OptionsUiEffect()
}