package com.fiz.wisecrypto.data.repositories

import android.content.SharedPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    private val isAuth: Boolean
        get() = sharedPreferences.getBoolean(AUTH_KEY, false)

    private val authEmail: String?
        get() = sharedPreferences.getString(EMAIL_KEY, null)

    private val isPortfolioNotification: Boolean
        get() = sharedPreferences.getBoolean(PORTFOLIO_NOTIFICATION, true)

    private val isPopularNotification: Boolean
        get() = sharedPreferences.getBoolean(POPULAR_NOTIFICATION, true)

    private val isWatchlistNotification: Boolean
        get() = sharedPreferences.getBoolean(WATCHLIST_NOTIFICATION, true)

    private val isPromotionsNotification: Boolean
        get() = sharedPreferences.getBoolean(PROMOTIONS_NOTIFICATION, true)

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

    suspend fun getIsPortfolioNotification(): Boolean {
        return withContext(dispatcher) {
            isPortfolioNotification
        }
    }

    suspend fun getIsPopularNotification(): Boolean {
        return withContext(dispatcher) {
            isPopularNotification
        }
    }

    suspend fun getIsWatchlistNotification(): Boolean {
        return withContext(dispatcher) {
            isWatchlistNotification
        }
    }

    suspend fun getIsPromotionsNotification(): Boolean {
        return withContext(dispatcher) {
            isPromotionsNotification
        }
    }

    suspend fun setIsPortfolioNotification(value: Boolean): Boolean {
        return withContext(dispatcher) {
            try {
                sharedPreferences.edit()
                    .putBoolean(PORTFOLIO_NOTIFICATION, value)
                    .apply()
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    suspend fun setIsPopularNotification(value: Boolean): Boolean {
        return withContext(dispatcher) {
            try {
                sharedPreferences.edit()
                    .putBoolean(POPULAR_NOTIFICATION, value)
                    .apply()
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    suspend fun setIsWatchlistNotification(value: Boolean): Boolean {
        return withContext(dispatcher) {
            try {
                sharedPreferences.edit()
                    .putBoolean(WATCHLIST_NOTIFICATION, value)
                    .apply()
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    suspend fun setIsPromotionsNotification(value: Boolean): Boolean {
        return withContext(dispatcher) {
            try {
                sharedPreferences.edit()
                    .putBoolean(PROMOTIONS_NOTIFICATION, value)
                    .apply()
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    suspend fun authCompleted(email: String) {
        return withContext(dispatcher) {
            sharedPreferences.edit()
                .putBoolean(AUTH_KEY, true)
                .putString(EMAIL_KEY, email.trim().lowercase())
                .apply()
        }
    }

    suspend fun exit() {
        return withContext(dispatcher) {
            sharedPreferences.edit()
                .putBoolean(AUTH_KEY, false)
                .putString(EMAIL_KEY, null)
                .apply()
        }
    }

    companion object {
        const val AUTH_KEY = "auth"
        const val EMAIL_KEY = "email"
        const val PORTFOLIO_NOTIFICATION = "PORTFOLIO_NOTIFICATION"
        const val POPULAR_NOTIFICATION = "POPULAR_NOTIFICATION"
        const val WATCHLIST_NOTIFICATION = "WATCHLIST_NOTIFICATION"
        const val PROMOTIONS_NOTIFICATION = "PROMOTIONS_NOTIFICATION"
    }
}