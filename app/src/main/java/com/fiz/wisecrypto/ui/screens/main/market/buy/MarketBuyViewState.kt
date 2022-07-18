package com.fiz.wisecrypto.ui.screens.main.market.buy

data class MarketBuyViewState(
    val icon: String = "",
    val valueBalance: String = "10",

    val nameCurrency: String = "Dollar",
    val symbolCurrency: String = "$",
    val currencyForBuy: String = "5",

    val valueCoin: String = "0.000001",
    val nameCoin: String = "Bitcoin",
    val symbolCoin: String = "BTC",
    val isLoading: Boolean = false
)