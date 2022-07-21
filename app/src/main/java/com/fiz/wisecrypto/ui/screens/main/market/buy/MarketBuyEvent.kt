package com.fiz.wisecrypto.ui.screens.main.market.buy

sealed class MarketBuyEvent {
    object BackButtonClicked : MarketBuyEvent()
    object AddBalanceClicked : MarketBuyEvent()
    object BuyButtonClicked : MarketBuyEvent()
    data class ValueCurrencyChanged(val value: String) : MarketBuyEvent()
}