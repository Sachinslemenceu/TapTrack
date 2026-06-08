package com.slemenceu.taptrack.features.mousepad.ui.settings_screen.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slemenceu.taptrack.features.authentication.domain.AuthRepository
import com.slemenceu.taptrack.features.authentication.domain.models.UserProfile
import com.slemenceu.taptrack.features.authentication.domain.usecase.UpdateProfileUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<ProfileUiEffect>()
    val uiEffect: SharedFlow<ProfileUiEffect> = _uiEffect.asSharedFlow()

    init {
        observeUserInfo()
    }

    private fun observeUserInfo() {
        viewModelScope.launch {
            combine(
                authRepository.readUserName(),
                authRepository.readUserEmail()
            ) { name, email ->
                Log.d("ProfileViewModel", "Name: $name, Email: $email")
                _uiState.update { it.copy(name = name, email = email) }
            }.collect()
        }
    }

    fun onEvent(event: ProfileUiEvent) {
        when (event) {
            is ProfileUiEvent.OnNameChanged -> {
                _uiState.update { it.copy(name = event.name) }
            }
            is ProfileUiEvent.OnEmailChanged -> {
                _uiState.update { it.copy(email = event.email) }
            }
            ProfileUiEvent.OnUpdateProfileClicked -> {
                updateProfile()
            }
            ProfileUiEvent.DismissSuccessMessage -> {
                _uiState.update { it.copy(isSuccess = false, errorMessage = null) }
            }
        }
    }

    private fun updateProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val profile = UserProfile(name = _uiState.value.name, email = _uiState.value.email)
            val success = updateProfileUseCase(profile)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    isSuccess = success,
                    errorMessage = if (success) null else "Failed to update profile"
                )
            }
            if (success) {
                _uiEffect.emit(ProfileUiEffect.ShowToast("Profile updated successfully"))
            }
        }
    }
}
