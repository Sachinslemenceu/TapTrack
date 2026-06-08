package com.slemenceu.taptrack.features.authentication.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "auth_pref")

class AuthStatus(val context: Context) {

    val AUTH_KEY = booleanPreferencesKey("auth_key")
    val FIRST_LOGIN_KEY = booleanPreferencesKey("first_login_key")
    val NAME_KEY = stringPreferencesKey("user_name")
    val EMAIL_KEY = stringPreferencesKey("user_email")

    suspend fun saveAuthStatus(isLoggedIn: Boolean){
        context.dataStore.edit {
            it[AUTH_KEY] = isLoggedIn
        }
    }
    fun readAuthStatus(): Flow<Boolean> {
        return context.dataStore.data.map {
            it[AUTH_KEY] ?: false
        }
    }

    suspend fun saveFirstLoginStatus(hasLoggedInBefore: Boolean){
        context.dataStore.edit {
            it[FIRST_LOGIN_KEY] = hasLoggedInBefore
        }
    }
    fun readFirstLoginStatus(): Flow<Boolean> {
        return context.dataStore.data.map {
            it[FIRST_LOGIN_KEY] ?: false
        }
    }

    suspend fun saveProfileInfo(name: String, email: String) {
        context.dataStore.edit {
            it[NAME_KEY] = name
            it[EMAIL_KEY] = email
        }
    }

    suspend fun saveName(name: String) {
        context.dataStore.edit {
            it[NAME_KEY] = name
        }
    }

    suspend fun saveEmail(email: String) {
        context.dataStore.edit {
            it[EMAIL_KEY] = email
        }
    }

    fun readUserName(): Flow<String> {
        return context.dataStore.data.map {
            it[NAME_KEY] ?: ""
        }
    }

    fun readUserEmail(): Flow<String> {
        return context.dataStore.data.map {
            it[EMAIL_KEY] ?: ""
        }
    }

    suspend fun clearProfileInfo() {
        context.dataStore.edit {
            it.remove(NAME_KEY)
            it.remove(EMAIL_KEY)
        }
    }
}