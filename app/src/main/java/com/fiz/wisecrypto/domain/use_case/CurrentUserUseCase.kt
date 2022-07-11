package com.fiz.wisecrypto.domain.use_case

import com.fiz.wisecrypto.data.repositories.SettingsRepositoryImpl
import com.fiz.wisecrypto.data.repositories.UserRepositoryImpl
import com.fiz.wisecrypto.domain.models.Coin
import com.fiz.wisecrypto.domain.models.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrentUserUseCase @Inject constructor(
    private val settingsRepository: SettingsRepositoryImpl,
    private val userRepository: UserRepositoryImpl,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend fun getCurrentUser(): User? {
        return withContext(dispatcher) {
            var user: User? = null
            val email = settingsRepository.getAuthEmail()
            if (email != null) {
                user = userRepository.getUserInfo(email)
            }
            user
        }
    }

    fun getCoinsWatchList(watchList: List<String>, coins: List<Coin>) =
        coins.filter { watchList.contains(it.id) }
}