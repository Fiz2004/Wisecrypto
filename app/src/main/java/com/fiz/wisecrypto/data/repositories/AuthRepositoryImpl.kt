package com.fiz.wisecrypto.data.repositories

import android.content.SharedPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    private val isAuth: Boolean
        get() = sharedPreferences.getBoolean(AUTH_KEY, false)

    private val authEmail:String?
        get() = sharedPreferences.getString(EMAIL_KEY, null)

    suspend fun getAuthStatus(): Boolean {
        return withContext(dispatcher) {
            isAuth
        }
    }

    suspend fun getAuthEmail(): String? {
        return withContext(dispatcher) {
            authEmail
        }
    }

    fun authCompleted(email:String) {
        sharedPreferences.edit()
            .putBoolean(AUTH_KEY, true)
            .putString(EMAIL_KEY, email.trim().lowercase())
            .apply()
    }

    fun authExit() {
        sharedPreferences.edit()
            .putBoolean(AUTH_KEY, false)
            .putString(EMAIL_KEY, null)
            .apply()
    }

    companion object {
        const val AUTH_KEY = "auth"
        const val EMAIL_KEY = "email"
    }
}