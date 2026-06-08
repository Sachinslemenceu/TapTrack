package com.slemenceu.taptrack.features.authentication.domain.usecase

import com.slemenceu.taptrack.features.authentication.domain.AuthRepository
import com.slemenceu.taptrack.features.authentication.domain.models.UserProfile

class UpdateProfileUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(profile: UserProfile): Boolean {
        return repository.updateProfile(profile)
    }
}
