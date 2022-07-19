package com.fiz.wisecrypto.ui.screens.main.market.buy

data class MarketBuyViewState(
    val valueBalance: String = "10",

    val nameCurrency: String = "Dollar",
    val symbolCurrency: String = "$",
    val currencyForBuy: String = "",

    val valueCoin: String = "",
    val nameCoin: String = "",
    val symbolCoin: String = "",
    val isLoading: Boolean = false
)