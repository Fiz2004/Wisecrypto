package com.fiz.wisecrypto.data.repositories

import com.fiz.wisecrypto.data.data_source.UserLocalDataSourceImpl
import com.fiz.wisecrypto.data.entity.TransactionEntity
import com.fiz.wisecrypto.data.entity.toActiveEntity
import com.fiz.wisecrypto.data.entity.toUserEntity
import com.fiz.wisecrypto.domain.models.Active
import com.fiz.wisecrypto.domain.models.User
import com.fiz.wisecrypto.domain.models.transaction.StatusTransaction
import com.fiz.wisecrypto.domain.models.transaction.TypeTransaction
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

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
        balance: Double,
    ): Boolean {
        return withContext(dispatcher) {
            try {
                val user = User.create(
                    fullName, numberPhone, userName, email, balance
                )
                val userEntity = user.toUserEntity(password)
                userLocalDataSource.saveUser(userEntity)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    suspend fun isUser(
        email: String,
        password: String
    ): Boolean {
        return withContext(dispatcher) {
            val checkEmail = email.trim().lowercase()
            val checkPassword = password.trim().lowercase()
            userLocalDataSource.checkUser(checkEmail, checkPassword)
        }
    }

    fun observeUser(email: String): Flow<User?> {
        val checkEmail = email.trim().lowercase()
        return userLocalDataSource.observeUser(checkEmail).map { it?.toUser() }
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

    suspend fun addWatchList(email: String, name: String): Boolean {
        return withContext(dispatcher) {
            val checkEmail = email.trim().lowercase()
            val user = userLocalDataSource.getUser(checkEmail) ?: return@withContext false
            val newWatchList = user.watchList.toMutableList()
            newWatchList.add(name)
            userLocalDataSource.changeWatchList(checkEmail, newWatchList)
        }
    }

    suspend fun removeWatchList(email: String, name: String): Boolean {
        return withContext(dispatcher) {
            val checkEmail = email.trim().lowercase()
            val user = userLocalDataSource.getUser(checkEmail) ?: return@withContext false
            val newWatchList = user.watchList.toMutableList()
            newWatchList.remove(name)
            userLocalDataSource.changeWatchList(checkEmail, newWatchList)
        }
    }

    suspend fun sellActive(
        user: User,
        idCoin: String,
        userCoinForSell: Double,
        priceCurrency: Double
    ): Boolean {
        return withContext(dispatcher) {
            val userActives = user.actives.toMutableList()
            val active = userActives.find { it.id == idCoin } ?: return@withContext false
            active.sell(userCoinForSell)
            val newBalance = user.plus(priceCurrency)
            if (active.isEmpty)
                userActives.remove(active)
            val activesEntity = userActives.map { it.toActiveEntity(user.email) }
            val transactionEntity = TransactionEntity(
                status = StatusTransaction.Process,
                type = TypeTransaction.Sell(userCoinForSell, priceCurrency, idCoin),
                id = "TP-" + Random.nextInt(10000).toString(),
                emailId = user.email,
                data = LocalDateTime.now()
            )
            userLocalDataSource.saveActivesAndBalance(
                user.email,
                activesEntity,
                newBalance,
                transactionEntity
            )
        }
    }

    suspend fun buyActive(
        user: User,
        idCoin: String,
        currency: Double,
        valueCoin: Double
    ): Boolean {
        return withContext(dispatcher) {
            val newActives = user.actives.toMutableList()
            val active = newActives.find { it.id == idCoin }
            val newBalance = user.minus(currency)
            val priceForBuy = currency / valueCoin
            if (active == null) {
                newActives.add(
                    Active.create(idCoin = idCoin, count = valueCoin, priceForBuy = priceForBuy)
                )
            } else {
                active.buy(valueCoin, priceForBuy)
            }
            val transactionEntity = TransactionEntity(
                status = StatusTransaction.Process,
                type = TypeTransaction.Buy(currency, valueCoin, idCoin),
                id = "TP-" + Random.nextInt(10000).toString(),
                emailId = user.email,
                data = LocalDateTime.now()
            )
            userLocalDataSource.saveActivesAndBalance(
                user.email,
                newActives.map { it.toActiveEntity(user.email) },
                newBalance,
                transactionEntity
            )
        }
    }

    // comission добавлено на будущее, когда подключим API для ввода денег
    suspend fun addBalance(
        user: User,
        currency: Double,
        comission: Double
    ): Flow<StatusProcessTransaction> {
        return flow {
            val id = "TS-" + Random.nextInt(10000).toString()
            emit(StatusProcessTransaction.Init(id))
            val newBalance = user.plus(currency)
            val transactionEntity = TransactionEntity(
                status = StatusTransaction.Process,
                type = TypeTransaction.AddBalance(currency),
                id = id,
                emailId = user.email,
                data = LocalDateTime.now()
            )
            if (userLocalDataSource.saveBalance(
                    user.email,
                    newBalance,
                    transactionEntity
                )
            )
                emit(StatusProcessTransaction.Success)
            else
                emit(StatusProcessTransaction.Failed)
        }.flowOn(dispatcher)
    }

    suspend fun cashBalance(user: User, currency: Double): Boolean {
        return withContext(dispatcher) {
            val newBalance = user.minus(currency)
            val transactionEntity = TransactionEntity(
                status = StatusTransaction.Process,
                type = TypeTransaction.CashBalance(currency),
                id = "TS-" + Random.nextInt(10000).toString(),
                emailId = user.email,
                data = LocalDateTime.now()
            )
            userLocalDataSource.saveBalance(
                user.email,
                newBalance,
                transactionEntity
            )
        }
    }
}

sealed class StatusProcessTransaction {
    object Success : StatusProcessTransaction()
    object Failed : StatusProcessTransaction()
    data class Init(val code: String) : StatusProcessTransaction()
}