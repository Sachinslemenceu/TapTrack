package com.slemenceu.taptrack.features.mousepad.ui.trackpad_screen

sealed class TrackpadUiEvent {
    data class SendTrackpadMove(val dx: Int, val dy: Int): TrackpadUiEvent()
    data class SendClick(val rightClick: Boolean): TrackpadUiEvent()
}