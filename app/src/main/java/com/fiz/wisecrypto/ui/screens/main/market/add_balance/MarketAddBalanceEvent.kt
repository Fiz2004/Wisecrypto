package com.fiz.wisecrypto.ui.screens.main.market.add_balance

sealed class MarketAddBalanceEvent {
    object BackButtonClicked : MarketAddBalanceEvent()
    object PayButtonClicked : MarketAddBalanceEvent()
    object PaymentClicked : MarketAddBalanceEvent()
    data class ValueCurrencyChanged(val value: String) : MarketAddBalanceEvent()
}