package com.fiz.wisecrypto.domain.models

import com.fiz.wisecrypto.BuildConfig
import com.fiz.wisecrypto.domain.models.transaction.Transaction

private val watchListDefault = listOf(
    "bitcoin",
    "ethereum",
    "litecoin",
    "solana",
    "binancecoin",
    "ripple"
)

private val activesDefault = if (BuildConfig.BUILD_TYPE == "debug")
    listOf(
        Active.create(
            "bitcoin",
            0.0012,
            20000.0
        ),
        Active.create(
            "ethereum",
            0.009,
            1000.0
        )
    )
else
    listOf()


class User private constructor(
    val fullName: String = "",
    val numberPhone: String = "",
    val userName: String = "",
    val email: String = "",
    val photo: String = "",
    private val balance: Long = 0,
    val watchList: List<String> = listOf(),
    var actives: List<Active> = listOf(),
    var transactions: List<Transaction> = listOf()
) {

    val balanceUi: Double = balance / BALANCE_ACCURACY

    val balanceForSaveEntity
        get() = balance


    fun plus(price: Double): Long {
        return (balance + price * BALANCE_ACCURACY).toLong()
    }

    fun minus(currency: Double): Long {
        return (balance - currency * BALANCE_ACCURACY).toLong()
    }

    companion object {
        private const val BALANCE_ACCURACY = 100.0

        fun create(
            fullName: String,
            numberPhone: String,
            userName: String,
            email: String,
            balance: Double,
            watchList: List<String> = watchListDefault,
            actives: List<Active> = activesDefault,
            transactions: List<Transaction> = listOf()
        ): User {
            val newBalance = (balance * BALANCE_ACCURACY).toLong()
            return create(
                fullName, numberPhone, userName, email, newBalance, watchList, actives, transactions
            )
        }

        fun create(
            fullName: String,
            numberPhone: String,
            userName: String,
            email: String,
            balance: Long,
            watchList: List<String> = watchListDefault,
            actives: List<Active> = activesDefault,
            transactions: List<Transaction> = listOf()
        ): User {
            return User(
                fullName = fullName.trim(),
                numberPhone = numberPhone.trim().filter { it.isDigit() },
                userName = userName.trim(),
                email = email.trim().lowercase(),
                watchList = watchList,
                balance = balance,
                actives = actives,
                transactions = transactions
            )
        }
    }
}