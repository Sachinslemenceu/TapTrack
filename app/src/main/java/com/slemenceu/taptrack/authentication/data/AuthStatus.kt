package com.slemenceu.taptrack.authentication.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "auth_pref")

class AuthStatus(val context: Context) {

    val AUTH_KEY = booleanPreferencesKey("auth_key")

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
}