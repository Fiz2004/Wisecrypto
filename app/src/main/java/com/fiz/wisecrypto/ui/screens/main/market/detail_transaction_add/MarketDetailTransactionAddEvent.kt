package com.fiz.wisecrypto.ui.screens.main.market.detail_transaction_add

sealed class MarketDetailTransactionAddEvent {
    object BackButtonClicked : MarketDetailTransactionAddEvent()
    object CancelButtonClicked : MarketDetailTransactionAddEvent()
    object CopyButtonClicked : MarketDetailTransactionAddEvent()
    object OpenInfo1Clicked : MarketDetailTransactionAddEvent()
    object OpenInfo2Clicked : MarketDetailTransactionAddEvent()
    object OpenInfo3Clicked : MarketDetailTransactionAddEvent()
    data class InitTransaction(val currency: Double, val commission: Double) :
        MarketDetailTransactionAddEvent()
}