package com.fiz.wisecrypto.data.repositories

import com.fiz.wisecrypto.data.data_source.UserLocalDataSourceImpl
import com.fiz.wisecrypto.data.entity.UserEntity
import com.fiz.wisecrypto.domain.models.Coin
import com.fiz.wisecrypto.domain.models.User
import com.fiz.wisecrypto.ui.screens.main.models.ActiveUi
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

    val watchList: List<String> =
        listOf("bitcoin", "ethereum", "litecoin", "solana", "binancecoin", "ripple")
    val portfolio: List<Active> = listOf(
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

    data class Active(
        val id: String = "",
        val count: Double = 0.0,
        val countForBuy: Double = 0.0
    ) {
        fun toActiveUi(data: List<Coin>?): ActiveUi {
            val current = data?.first { it.id == id } ?: return ActiveUi()
            val divided = current.currentPrice / countForBuy * 100
            val percent = if (divided > 0) divided - 100 else 100 - divided
            val portfolio = count * (current.currentPrice)
            return ActiveUi(
                abbreviated = current.abbreviated.uppercase(),
                name = current.name,
                icon = current.icon,
                portfolio = "\$${"%.2f".format(portfolio)}",
                equivalent = "${"%.4f".format(count)} ${current.abbreviated.uppercase()}",
                isUpDirectChangePercentage = percent > 0.0,
                changePercentage = "${"%.1f".format(percent)}%"
            )
        }
    }

    suspend fun saveUser(
        fullName: String,
        numberPhone: String,
        userName: String,
        email: String,
        password: String
    ): Boolean {
        return withContext(dispatcher) {
            try {
                val userEntity = UserEntity(
                    fullName = fullName.trim(),
                    numberPhone = numberPhone.trim().filter { it.isDigit() },
                    userName = userName.trim(),
                    email = email.trim().lowercase(),
                    password = password.trim().lowercase()
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