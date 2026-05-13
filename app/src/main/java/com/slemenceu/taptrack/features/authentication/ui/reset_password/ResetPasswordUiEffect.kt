package com.slemenceu.taptrack.features.authentication.ui.reset_password

sealed class ResetPasswordUiEffect {
    data class ShowToast(val message: String) : ResetPasswordUiEffect()
    object OnResetLinkSent : ResetPasswordUiEffect()
}