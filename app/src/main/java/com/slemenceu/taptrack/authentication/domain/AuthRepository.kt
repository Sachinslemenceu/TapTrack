package com.slemenceu.taptrack.authentication.domain

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Boolean
    suspend fun register(email: String, password: String): Boolean
    suspend fun saveAuthStatus(isLoggedIn: Boolean)
    suspend fun readAuthStatus(): Flow<Boolean>
}