package com.fiz.wisecrypto.ui.screens.main.market.detail_transaction_cash

sealed class MarketDetailTransactionCashEvent {
    object BackButtonClicked : MarketDetailTransactionCashEvent()
    object CancelButtonClicked : MarketDetailTransactionCashEvent()
    data class InitTransaction(val currency: Double, val commission: Double) :
        MarketDetailTransactionCashEvent()
}