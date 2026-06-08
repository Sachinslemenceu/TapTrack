package com.slemenceu.taptrack.features.authentication.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.slemenceu.taptrack.features.authentication.domain.models.UserProfile
import kotlinx.coroutines.tasks.await

class AuthService() {
    private val auth = FirebaseAuth.getInstance()

    suspend fun login(email: String, password: String): UserProfile? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            var user = result.user

            if (user != null) {
                // Force reload
                user.reload().await()
                // Fetch the absolute latest user instance
                val updatedUser = auth.currentUser

                // If displayName is still null, try to get it from providerData
                val displayName = updatedUser?.displayName
                    ?: updatedUser?.providerData?.firstOrNull { it.displayName != null }?.displayName

                Log.d(
                    "AuthService",
                    "User logged in userdetail: $displayName, ${updatedUser?.email}"
                )

                UserProfile(
                    name = displayName ?: "",
                    email = updatedUser?.email ?: ""
                )
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("AuthService", "Login failed: ${e.message}")
            null
        }
    }

    suspend fun register(name: String, email: String, password: String): Boolean {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = auth.currentUser // Get fresh user instance

            Log.d("AuthService", "User created, now updating profile with name: $name")

            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()

            // Wait for profile update to complete
            user?.updateProfile(profileUpdates)?.await()

            // Critical: Force a reload immediately to verify the update
            user?.reload()?.await()
            val verifiedUser = auth.currentUser

            Log.d("AuthService", "Verification after register - Name in Firebase: ${verifiedUser?.displayName}")

            true
        } catch (e: Exception) {
            Log.e("AuthService", "Registration failed: ${e.message}")
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

    fun logout() {
        auth.signOut()
    }

    suspend fun updateProfile(profile: UserProfile): Boolean {
        return try {
            val user = auth.currentUser ?: return false
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(profile.name)
                .build()

            user.updateProfile(profileUpdates).await()
            user.updateEmail(profile.email).await()

            // Reload to sync changes locally
            user.reload().await()

            true
        } catch (e: Exception) {
            Log.e("AuthService", "Update profile failed: ${e.message}")
            false
        }
    }

    suspend fun updatePassword(password: String): Boolean {
        return try {
            val user = auth.currentUser ?: return false
            user.updatePassword(password).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
