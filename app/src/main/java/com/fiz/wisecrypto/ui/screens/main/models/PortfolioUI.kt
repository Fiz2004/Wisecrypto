package com.fiz.wisecrypto.ui.screens.main.models

class PortfolioUI(
    val actives: List<ActiveUi> = listOf(),
    val balancePortfolio: String = "",
    val isPricePortfolioIncreased: Boolean = true,
    val percentageChangedBalance: String = "",
    val totalReturn: String = ""
)