package com.fiz.wisecrypto.ui.screens.main.profile.list_transactions.models

sealed class TypeTransaction {
    data class Balance(val value: Double) : TypeTransaction()
    data class Buy(val value: Double) : TypeTransaction()
    data class Sell(val value: Double) : TypeTransaction()
}