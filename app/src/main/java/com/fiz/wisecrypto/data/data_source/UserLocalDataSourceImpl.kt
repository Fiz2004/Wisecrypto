package com.fiz.wisecrypto.data.data_source

import com.fiz.wisecrypto.data.database.dao.UserDao
import com.fiz.wisecrypto.data.entity.ActiveEntity
import com.fiz.wisecrypto.data.entity.UserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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
                userDao.insertActives(userEntity.actives)
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

    suspend fun getUser(checkEmail: String): UserEntity? {
        return withContext(dispatcher) {
            try {
                userDao
                    .getUserByEmail(checkEmail)
                    .copy(actives = userDao.getActives(checkEmail))
            } catch (e: Exception) {
                null
            }
        }
    }

    fun observeUser(checkEmail: String): Flow<UserEntity?> {
        return flow {
            try {
                emitAll(userDao.observeUserByEmail(checkEmail).map {
                    it.copy(actives = userDao.getActives(checkEmail))
                })
            } catch (e: Exception) {
                emit(null)
            }
        }
    }

    suspend fun changePassword(checkEmail: String, checkNewPassword: String): Boolean {
        return withContext(dispatcher) {
            try {
                userDao.changePassword(checkEmail, checkNewPassword)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    suspend fun changeEmail(checkOldEmail: String, checkNewEmail: String): Boolean {
        return withContext(dispatcher) {
            try {
                val user = userDao.getUserByEmail(checkOldEmail)
                userDao.insert(user.copy(email = checkNewEmail))
                userDao.delete(user)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    suspend fun changeWatchList(email: String, watchList: List<String>): Boolean {
        return withContext(dispatcher) {
            try {
                userDao.changeWatchList(email, watchList)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    suspend fun saveActivesAndBalance(
        email: String,
        actives: List<ActiveEntity>,
        balance: Double
    ): Boolean {
        return withContext(dispatcher) {
            try {
                userDao.saveActivesAndSaveBalance(email, actives, balance)
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}