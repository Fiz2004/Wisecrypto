package com.fiz.wisecrypto.ui.screens.main.market.sell

data class MarketSellViewState(
    val icon: String = "",
    val valueActiveCoin: String = "",
    val priceCurrency: String = "",
    val nameCoin: String = "",
    val nameCurrency: String = "",
    val symbolCurrency: String = "$",
    val userCoinForSell: String = "0",
    val symbolCoin: String = "",
    val isLoading: Boolean = false
)