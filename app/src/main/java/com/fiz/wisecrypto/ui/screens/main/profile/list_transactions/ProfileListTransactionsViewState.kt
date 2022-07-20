package com.fiz.wisecrypto.ui.screens.main.profile.list_transactions

import com.fiz.wisecrypto.domain.models.transaction.Transaction

data class ProfileListTransactionsViewState(
    val fullName: String = "",
    val photo: String = "",
    val selectedChipNumber: Int = 0,
    val transactions: List<Transaction> = listOf()
)