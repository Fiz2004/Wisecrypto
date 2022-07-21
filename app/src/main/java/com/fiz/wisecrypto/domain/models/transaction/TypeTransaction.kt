package com.fiz.wisecrypto.domain.models.transaction

sealed class TypeTransaction {
    data class AddBalance(val value: Double) : TypeTransaction()
    data class CashBalance(val value: Double) : TypeTransaction()
    data class Buy(val currency: Double, val coin: Double, val id: String) : TypeTransaction()
    data class Sell(val coin: Double, val currency: Double, val id: String) : TypeTransaction()
}