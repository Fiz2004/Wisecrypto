package com.fiz.wisecrypto.data.data_source

import com.fiz.wisecrypto.data.database.dao.UserDao
import com.fiz.wisecrypto.data.entity.UserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend fun saveUser(userEntity: UserEntity): Boolean {
        return withContext(dispatcher) {
            try {
                userDao.insert(userEntity)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    suspend fun checkUser(checkEmail: String, checkPassword: String): Boolean {
        return withContext(dispatcher) {
            try {
                userDao.isValidateEmailPassword(checkEmail, checkPassword)
            } catch (e: Exception) {
                false
            }
        }
    }

    suspend fun loadUser(checkEmail: String): UserEntity? {
        return withContext(dispatcher) {
            try {
                userDao.getUserByEmail(checkEmail)
            } catch (e: Exception) {
                null
            }
        }
    }
}