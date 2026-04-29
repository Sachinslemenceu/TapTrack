package com.slemenceu.taptrack.authentication.ui.reset_password

sealed class ResetPasswordUiEvent {
    object OnSendResetLinkClicked : ResetPasswordUiEvent()
    class OnEmailChanged(val email: String) : ResetPasswordUiEvent()
}