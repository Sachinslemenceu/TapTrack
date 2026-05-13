package com.slemenceu.taptrack.features.authentication.ui.login_screen

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
)
