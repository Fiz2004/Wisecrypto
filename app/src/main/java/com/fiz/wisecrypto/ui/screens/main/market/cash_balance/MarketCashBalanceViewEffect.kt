package com.fiz.wisecrypto.ui.screens.main.market.cash_balance

sealed class MarketCashBalanceViewEffect {
    object MoveReturn : MarketCashBalanceViewEffect()
    data class MoveMarketDetailTransactionCashScreen(val currency: Double, val commission: Double) :
        MarketCashBalanceViewEffect()

    data class ShowError(val message: String?) : MarketCashBalanceViewEffect()
}