package com.fiz.wisecrypto.ui.screens.main.models

class PortfolioUI(
    val actives: List<ActiveUi> = listOf(),
    val pricePortfolio: String = "",
    val pricePortfolioIncreased: Boolean = true,
    val changePercentagePricePortfolio: String = "",
    val totalReturn: String = ""
)