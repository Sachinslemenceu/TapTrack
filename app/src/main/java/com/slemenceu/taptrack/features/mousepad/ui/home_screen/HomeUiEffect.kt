package com.slemenceu.taptrack.features.mousepad.ui.home_screen

import android.content.Intent

sealed class HomeUiEffect {
    object NavigateToMousepad : HomeUiEffect()
    object NavigateToPcGuide : HomeUiEffect()
    object NavigateToOptions : HomeUiEffect()

}