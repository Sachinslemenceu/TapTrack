package com.slemenceu.taptrack.authentication.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

class AuthService() {
    private val auth = FirebaseAuth.getInstance()

    suspend fun login(email: String, password: String): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, password)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }
    suspend fun register(name: String,email: String, password: String): Boolean {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            Log.d("AuthService", "User registered with email: $email, name: $name")
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()
            result.user?.updateProfile(profileUpdates)?.await()
            true
        } catch (e: Exception) {
            Log.d("AuthService", "Registration failed for email: $email, name: $name, error: ${e.message}")
            false
        }
    }

    suspend fun sendResetPasswordLink(email: String): Boolean {
        return try {
            auth.sendPasswordResetEmail(email).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}