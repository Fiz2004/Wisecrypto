package com.fiz.wisecrypto.ui.screens.main.market.detail_transaction_sell

sealed class MarketDetailTransactionSellEvent {
    object BackButtonClicked : MarketDetailTransactionSellEvent()
    data class InitTransaction(
        val idCoin: String,
        val userCoinForSell: Double,
        val priceCurrency: Double
    ) :
        MarketDetailTransactionSellEvent()
}