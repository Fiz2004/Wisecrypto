package com.fiz.wisecrypto.data.repositories

import android.content.SharedPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    private val isAuth: Boolean
        get() = sharedPreferences.getBoolean(AUTH_KEY, false)

    suspend fun getAuthStatus(): Boolean {
        return withContext(dispatcher) {
            isAuth
        }
    }

    fun authCompleted() {
        sharedPreferences.edit()
            .putBoolean(AUTH_KEY, true)
            .apply()
    }

    fun authExit() {
        sharedPreferences.edit()
            .putBoolean(AUTH_KEY, false)
            .apply()
    }

    companion object {
        const val AUTH_KEY = "auth"
    }
}