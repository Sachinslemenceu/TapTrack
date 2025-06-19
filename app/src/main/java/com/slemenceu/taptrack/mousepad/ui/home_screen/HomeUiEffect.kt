package com.slemenceu.taptrack.mousepad.ui.home_screen

import android.content.Intent

sealed class HomeUiEffect {
    object NavigateToLogin : HomeUiEffect()
    object NavigateToMousepad : HomeUiEffect()
    class onQrScanClicked(val intent: Intent) : HomeUiEffect()
    object onQrScanCancelled : HomeUiEffect()

}