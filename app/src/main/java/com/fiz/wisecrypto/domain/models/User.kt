package com.fiz.wisecrypto.domain.models

import com.fiz.wisecrypto.domain.models.transaction.Transaction

data class User(
    val fullName: String = "",
    val numberPhone: String = "",
    val userName: String = "",
    val email: String = "",
    val photo: String = "",
    val balance: Double = 0.0,
    val watchList: List<String> = listOf(),
    var actives: List<Active> = listOf(),
    var transactions: List<Transaction> = listOf()
)