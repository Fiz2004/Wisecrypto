package com.fiz.wisecrypto.domain.models.transaction

sealed class TypeTransaction {
    data class Balance(val value: Double) : TypeTransaction()
    data class Buy(val currency: Double, val coin: Double) : TypeTransaction()
    data class Sell(val coin: Double, val currency: Double) : TypeTransaction()
}