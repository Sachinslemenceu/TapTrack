package com.slemenceu.taptrack.authentication.ui.register_screen

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false
)
