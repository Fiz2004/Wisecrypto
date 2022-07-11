package com.fiz.wisecrypto.data.repositories

import com.fiz.wisecrypto.data.data_source.UserLocalDataSourceImpl
import com.fiz.wisecrypto.data.entity.UserEntity
import com.fiz.wisecrypto.domain.models.Active
import com.fiz.wisecrypto.domain.models.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSourceImpl,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend fun saveUser(
        fullName: String,
        numberPhone: String,
        userName: String,
        email: String,
        password: String,
        watchList: List<String> = listOf(
            "bitcoin",
            "ethereum",
            "litecoin",
            "solana",
            "binancecoin",
            "ripple"
        ),
        portfolio: List<Active> = listOf(
            Active(
                "bitcoin",
                0.0012,
                20000.0
            ),
            Active(
                "ethereum",
                0.009,
                1000.0
            )
        )
    ): Boolean {
        return withContext(dispatcher) {
            try {
                val userEntity = UserEntity(
                    fullName = fullName.trim(),
                    numberPhone = numberPhone.trim().filter { it.isDigit() },
                    userName = userName.trim(),
                    email = email.trim().lowercase(),
                    password = password.trim().lowercase(),
                    watchList = watchList,
                    portfolio = portfolio.map { it.toActiveEntity(email.trim().lowercase()) }
                )
                userLocalDataSource.saveUser(userEntity)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    suspend fun checkUser(
        email: String,
        password: String
    ): User? {
        return withContext(dispatcher) {
            val checkEmail = email.trim().lowercase()
            val checkPassword = password.trim().lowercase()
            if (userLocalDataSource.checkUser(checkEmail, checkPassword)) {
                val user = userLocalDataSource.loadUser(checkEmail)?.toUser()
                user
            } else {
                null
            }
        }
    }

    suspend fun getUserInfo(email: String): User? {
        return withContext(dispatcher) {
            val checkEmail = email.trim().lowercase()
            val user = userLocalDataSource.loadUser(checkEmail)?.toUser()
            user
        }
    }

    suspend fun changeEmailPassword(
        oldEmail: String,
        newEmail: String,
        oldPassword: String,
        newPassword: String
    ): Boolean {
        return withContext(dispatcher) {
            val checkOldEmail = oldEmail.trim().lowercase()
            val checkNewEmail = newEmail.trim().lowercase()
            val checkOldPassword = oldPassword.trim().lowercase()
            val checkNewPassword = newPassword.trim().lowercase()

            if (checkOldEmail != checkNewEmail)
                if (userLocalDataSource.checkUser(checkOldEmail, checkOldPassword)) {
                    if (!userLocalDataSource.changeEmail(checkOldEmail, checkNewEmail))
                        return@withContext false
                } else {
                    return@withContext false
                }

            if (userLocalDataSource.checkUser(checkNewEmail, checkOldPassword)) {
                userLocalDataSource.changePassword(checkNewEmail, checkNewPassword)
            } else {
                false
            }
        }
    }
}