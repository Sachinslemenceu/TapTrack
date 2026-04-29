package com.slemenceu.taptrack.authentication.ui.register_screen

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false
)
