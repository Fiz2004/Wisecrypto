package com.fiz.wisecrypto.ui.screens.main.market.buy

sealed class MarketBuyViewEffect {
    object MoveReturn : MarketBuyViewEffect()
    object AddBalanceClicked : MarketBuyViewEffect()
    data class ShowError(val message: String?) : MarketBuyViewEffect()
}