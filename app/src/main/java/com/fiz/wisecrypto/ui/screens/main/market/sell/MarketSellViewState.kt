package com.fiz.wisecrypto.ui.screens.main.market.sell

data class MarketSellViewState(
    val icon: String = "",
    val valueActiveCoin: String = "",
    val valueCurrency: String = "",
    val nameCoin: String = "",
    val nameCurrency: String = "",
    val symbolCurrency: String = "$",
    val coinForSell: String = "0",
    val symbolCoin: String = "",
    val isLoading: Boolean = false
)