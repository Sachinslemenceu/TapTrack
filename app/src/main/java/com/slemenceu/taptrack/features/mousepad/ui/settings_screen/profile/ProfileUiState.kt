package com.slemenceu.taptrack.features.mousepad.ui.settings_screen.profile

data class ProfileUiState(
    val name: String = "",
    val email: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)

sealed interface ProfileUiEvent {
    data class OnNameChanged(val name: String) : ProfileUiEvent
    data class OnEmailChanged(val email: String) : ProfileUiEvent
    object OnUpdateProfileClicked : ProfileUiEvent
    object DismissSuccessMessage : ProfileUiEvent
}

sealed interface ProfileUiEffect {
    object NavigateBack : ProfileUiEffect
    data class ShowToast(val message: String) : ProfileUiEffect
}
