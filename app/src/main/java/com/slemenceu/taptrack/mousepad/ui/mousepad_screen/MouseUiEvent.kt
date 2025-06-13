package com.slemenceu.taptrack.mousepad.ui.mousepad_screen

sealed class MouseUiEvent {
    data class SendMouseMove(val dx: Int, val dy: Int): MouseUiEvent()
    data class SendClick(val rightClick: Boolean): MouseUiEvent()
}