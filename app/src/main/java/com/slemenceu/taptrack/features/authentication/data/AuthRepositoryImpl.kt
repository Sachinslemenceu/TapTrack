package com.slemenceu.taptrack.features.authentication.data

import com.slemenceu.taptrack.features.authentication.domain.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    val authStatus: AuthStatus,
    val authService: AuthService
): AuthRepository {
    override suspend fun login(email: String, password: String): Boolean {
        return authService.login(email, password)
    }

    override suspend fun register(name: String,email: String, password: String): Boolean {
        return authService.register(name,email, password)
    }

    override suspend fun sendResetPasswordLink(email: String): Boolean {
        return authService.sendResetPasswordLink(email)
    }

    override suspend fun saveAuthStatus(isLoggedIn: Boolean) {
        return authStatus.saveAuthStatus(isLoggedIn)
    }

    override suspend fun readAuthStatus(): Flow<Boolean> {
        return authStatus.readAuthStatus()
    }
}