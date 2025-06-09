package com.slemenceu.taptrack.authentication.data

import com.slemenceu.taptrack.authentication.domain.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    val authStatus: AuthStatus,
    val authService: AuthService
): AuthRepository {
    override suspend fun login(email: String, password: String): Boolean {
        return authService.login(email, password)
    }

    override suspend fun register(email: String, password: String): Boolean {
        return authService.register(email, password)
    }

    override suspend fun saveAuthStatus(isLoggedIn: Boolean) {
        return authStatus.saveAuthStatus(isLoggedIn)
    }

    override suspend fun readAuthStatus(): Flow<Boolean> {
        return authStatus.readAuthStatus()
    }
}