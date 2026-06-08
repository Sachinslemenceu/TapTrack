package com.slemenceu.taptrack.features.authentication.domain.usecase

import com.slemenceu.taptrack.features.authentication.domain.AuthRepository

class LogoutUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        return repository.logout()
    }
}
