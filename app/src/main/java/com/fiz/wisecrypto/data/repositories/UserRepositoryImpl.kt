package com.fiz.wisecrypto.data.repositories

import com.fiz.wisecrypto.data.data_source.UserLocalDataSourceImpl
import com.fiz.wisecrypto.data.entity.ActiveEntity
import com.fiz.wisecrypto.data.entity.TransactionEntity
import com.fiz.wisecrypto.data.entity.UserEntity
import com.fiz.wisecrypto.data.entity.toActiveEntity
import com.fiz.wisecrypto.domain.models.Active
import com.fiz.wisecrypto.domain.models.StatusTransaction
import com.fiz.wisecrypto.domain.models.TypeTransaction
import com.fiz.wisecrypto.domain.models.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
                    balance = balance,
                    actives = portfolio.map { it.toActiveEntity(email.trim().lowercase()) }
                )
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

    suspend fun sellActive(email: String, idCoin: String, count: Double, price: Double): Boolean {
        return withContext(dispatcher) {
            val checkEmail = email.trim().lowercase()
            val user = userLocalDataSource.getUser(checkEmail) ?: return@withContext false
            val newActives = user.actives.toMutableList()
            val active = newActives.find { it.id == idCoin } ?: return@withContext false
            val newValue = active.count - count
            val newBalance = user.balance + price
            active.count = newValue
            if (active.count == 0.0)
                newActives.remove(active)
            val transactionEntity = TransactionEntity(
                status = StatusTransaction.Process,
                type = TypeTransaction.Sell(count, price),
                textDescription = "$count $idCoin -> ${price}$",
                id = "TS-" + Random.nextInt(10000).toString(),
                emailId = checkEmail,
                data = LocalDateTime.now()
            )
            userLocalDataSource.saveActivesAndBalance(
                checkEmail,
                newActives,
                newBalance,
                transactionEntity
            )
        }
    }

    suspend fun buyActive(
        email: String,
        idCoin: String,
        currency: Double,
        valueCoin: Double
    ): Boolean {
        return withContext(dispatcher) {
            val checkEmail = email.trim().lowercase()
            val user = userLocalDataSource.getUser(checkEmail) ?: return@withContext false
            val newActives = user.actives.toMutableList()
            val active = newActives.find { it.id == idCoin }
            val newBalance = user.balance - currency
            if (active == null) {
                val newValue = valueCoin
                newActives.add(
                    ActiveEntity(
                        id = idCoin,
                        count = newValue,
                        emailId = email,
                        priceForBuy = currency / valueCoin
                    )
                )
            } else {
                val newValue = active.count + valueCoin
                active.count = newValue
            }
            val transactionEntity = TransactionEntity(
                status = StatusTransaction.Process,
                type = TypeTransaction.Buy(currency, valueCoin),
                textDescription = "${currency}$ -> $valueCoin $idCoin",
                id = "TS-" + Random.nextInt(10000).toString(),
                emailId = checkEmail,
                data = LocalDateTime.now()
            )
            userLocalDataSource.saveActivesAndBalance(
                checkEmail,
                newActives,
                newBalance,
                transactionEntity
            )
        }
    }
}