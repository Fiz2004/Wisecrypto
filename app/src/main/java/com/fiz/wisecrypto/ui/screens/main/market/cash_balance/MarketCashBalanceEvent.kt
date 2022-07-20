package com.fiz.wisecrypto.ui.screens.main.market.cash_balance

sealed class MarketCashBalanceEvent {
    object BackButtonClicked : MarketCashBalanceEvent()
    object CashButtonClicked : MarketCashBalanceEvent()
    object PaymentClicked : MarketCashBalanceEvent()
    object Started : MarketCashBalanceEvent()
    object Stopped : MarketCashBalanceEvent()
    object OnRefresh : MarketCashBalanceEvent()
    object CashAll : MarketCashBalanceEvent()

    data class ValueCurrencyChanged(val value: String) : MarketCashBalanceEvent()
}