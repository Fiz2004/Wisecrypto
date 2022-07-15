package com.fiz.wisecrypto.ui.screens.main.market.sell

data class MarketSellViewState(
    val icon: String = "",
    val valueActiveCoin: String = "0.000012",
    val valueCurrency: String = "5",
    val nameCoin: String = "Bitcoin",
    val nameCurrency: String = "Dollar",
    val symbolCurrency: String = "$",
    val coinForSell: String = "0.000001",
    val symbolCoin: String = "BTC",
    val isLoading: Boolean = false
)