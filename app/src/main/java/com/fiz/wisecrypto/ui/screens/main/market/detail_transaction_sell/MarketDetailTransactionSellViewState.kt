package com.fiz.wisecrypto.ui.screens.main.market.detail_transaction_sell

import com.fiz.wisecrypto.domain.models.transaction.StatusTransaction
import com.fiz.wisecrypto.domain.models.transaction.Transaction
import com.fiz.wisecrypto.domain.models.transaction.TypeTransaction
import org.threeten.bp.LocalDateTime

data class MarketDetailTransactionSellViewState(
    val transaction: Transaction = Transaction(
        StatusTransaction.Process, TypeTransaction.Sell(0.0, 0.0, ""), "",
        LocalDateTime.now()
    ),
    val currencyForBuy: String = "",
    val commission: String = "",
    val total: String = "",
    val isLoading: Boolean = false,

    )