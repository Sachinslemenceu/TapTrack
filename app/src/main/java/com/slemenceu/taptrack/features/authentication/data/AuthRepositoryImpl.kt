package com.slemenceu.taptrack.features.authentication.data

import com.slemenceu.taptrack.Profile
import com.slemenceu.taptrack.features.authentication.domain.AuthRepository
import com.slemenceu.taptrack.features.authentication.domain.models.UserProfile
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val authStatus: AuthStatus,
    private val authService: AuthService
): AuthRepository {
    override suspend fun login(email: String, password: String): Boolean {
        val result = authService.login(email, password)
        if (result !=null) {
            authStatus.saveProfileInfo(result.name,result.email)
            return true
        }else{
            return false
        }
    }

    override suspend fun register(name: String, email: String, password: String): Boolean {
        val success = authService.register(name, email, password)
        if (success) {
            saveProfileInfo(name, email)
        }
        return success
    }

    override suspend fun sendResetPasswordLink(email: String): Boolean {
        return authService.sendResetPasswordLink(email)
    }

    override suspend fun saveAuthStatus(isLoggedIn: Boolean) {
        authStatus.saveAuthStatus(isLoggedIn)
    }

    override suspend fun readAuthStatus(): Flow<Boolean> {
        return authStatus.readAuthStatus()
    }

    override suspend fun saveFirstLoginStatus(hasLoggedInBefore: Boolean) {
        authStatus.saveFirstLoginStatus(hasLoggedInBefore)
    }

    override suspend fun readFirstLoginStatus(): Flow<Boolean> {
        return authStatus.readFirstLoginStatus()
    }

    override suspend fun logout(): Boolean {
        return try {
            authService.logout()
            saveAuthStatus(false)
            authStatus.clearProfileInfo()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun updateProfile(profile: UserProfile): Boolean {
        val success = authService.updateProfile(profile)
        if (success) {
            authStatus.saveProfileInfo(profile.name, profile.email)
        }
        return success
    }




    override suspend fun updatePassword(password: String): Boolean {
        return authService.updatePassword(password)
    }

    override suspend fun saveProfileInfo(name: String, email: String) {
        authStatus.saveProfileInfo(name, email)
    }

    override fun readUserName(): Flow<String> {
        return authStatus.readUserName()
    }

    override fun readUserEmail(): Flow<String> {
        return authStatus.readUserEmail()
    }
}
