package com.fiz.wisecrypto.ui.screens.main.market.sell

sealed class MarketSellEvent {
    object BackButtonClicked : MarketSellEvent()
    object SellAllButtonClicked : MarketSellEvent()
    object SellButtonClicked : MarketSellEvent()
    data class ValueCoinChanged(val value: String) : MarketSellEvent()
}