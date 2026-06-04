package com.slemenceu.taptrack.features.mousepad.ui.trackpad_screen

sealed class TrackpadUiEffect {
    object NavigateToHome : TrackpadUiEffect()
    object ConnectionLost: TrackpadUiEffect()
}