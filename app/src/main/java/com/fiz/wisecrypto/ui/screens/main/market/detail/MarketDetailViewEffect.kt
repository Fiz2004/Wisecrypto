package com.fiz.wisecrypto.ui.screens.main.market.detail

sealed class MarketDetailViewEffect {
    object MoveReturn : MarketDetailViewEffect()
    object MoveMarketSellScreen : MarketDetailViewEffect()
    object MoveMarketBuyScreen : MarketDetailViewEffect()
    data class ShowError(val message: String?) : MarketDetailViewEffect()
}