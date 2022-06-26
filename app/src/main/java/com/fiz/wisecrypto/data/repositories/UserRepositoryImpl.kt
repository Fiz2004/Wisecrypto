package com.fiz.wisecrypto.data.repositories

import com.fiz.wisecrypto.data.data_source.UserLocalDataSourceImpl
import com.fiz.wisecrypto.data.entity.UserEntity
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
}