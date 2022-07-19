package com.fiz.wisecrypto.ui.screens.main.market.add_balance

sealed class MarketAddBalanceEvent {
    object BackButtonClicked : MarketAddBalanceEvent()
    object PayButtonClicked : MarketAddBalanceEvent()
    object PaymentClicked : MarketAddBalanceEvent()
    object Started : MarketAddBalanceEvent()
    object Stopped : MarketAddBalanceEvent()
    object OnRefresh : MarketAddBalanceEvent()
    data class ValueCurrencyChanged(val value: String) : MarketAddBalanceEvent()
}