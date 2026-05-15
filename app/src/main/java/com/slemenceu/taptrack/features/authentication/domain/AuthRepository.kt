package com.slemenceu.taptrack.features.authentication.domain

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Boolean
    suspend fun register(name: String,email: String, password: String): Boolean
    suspend fun sendResetPasswordLink(email: String): Boolean
    suspend fun saveAuthStatus(isLoggedIn: Boolean)
    suspend fun readAuthStatus(): Flow<Boolean>
    suspend fun saveFirstLoginStatus(hasLoggedInBefore: Boolean)
    suspend fun readFirstLoginStatus(): Flow<Boolean>
}