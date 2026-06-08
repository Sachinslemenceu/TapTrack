package com.slemenceu.taptrack.features.authentication.domain.usecase

import com.slemenceu.taptrack.features.authentication.domain.AuthRepository

class UpdatePasswordUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(password: String): Boolean {
        return repository.updatePassword(password)
    }
}
