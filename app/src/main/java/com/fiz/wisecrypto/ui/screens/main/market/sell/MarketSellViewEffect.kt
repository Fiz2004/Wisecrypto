package com.fiz.wisecrypto.ui.screens.main.market.sell

sealed class MarketSellViewEffect {
    object MoveReturn : MarketSellViewEffect()
    data class ShowError(val message: String?) : MarketSellViewEffect()
    data class MoveMarketDetailTransactionSellScreen(
        val idCoin: String,
        val userCoinForSell: Double,
        val priceCurrency: Double
    ) : MarketSellViewEffect()
}