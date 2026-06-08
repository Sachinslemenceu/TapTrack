package com.slemenceu.taptrack.features.authentication.domain

import com.slemenceu.taptrack.Profile
import com.slemenceu.taptrack.features.authentication.domain.models.UserProfile
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Boolean
    suspend fun register(name: String, email: String, password: String): Boolean
    suspend fun sendResetPasswordLink(email: String): Boolean
    suspend fun saveAuthStatus(isLoggedIn: Boolean)
    suspend fun readAuthStatus(): Flow<Boolean>
    suspend fun saveFirstLoginStatus(hasLoggedInBefore: Boolean)
    suspend fun readFirstLoginStatus(): Flow<Boolean>
    suspend fun logout(): Boolean

    // Profile Management
    suspend fun updateProfile(profile: UserProfile): Boolean
    suspend fun updatePassword(password: String): Boolean
    suspend fun saveProfileInfo(name: String, email: String)
    fun readUserName(): Flow<String>
    fun readUserEmail(): Flow<String>
}
