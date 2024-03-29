package com.fiz.wisecrypto.ui.screens.main.market.cash_balance

import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.profile.payment.models.Payment

data class MarketCashBalanceViewState(
    val valueBalance: String = "",

    val symbolCurrency: String = "$",
    val currencyForBuy: String = "10",
    val commission: String = "",
    val total: String = "",

    val isLoading: Boolean = false,

    val payment: Payment = paymentsList[0]

)


private val paymentsList = listOf(
    Payment(R.drawable.payment_ic_ovo, "Ovo", "081264950021"),
    Payment(R.drawable.payment_ic_gopay, "Gopay", "081264950021"),
    Payment(R.drawable.payment_ic_mandiri, "Mandiri", "144202839134"),
)