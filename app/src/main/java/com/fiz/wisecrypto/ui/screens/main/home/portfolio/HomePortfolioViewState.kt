package com.fiz.wisecrypto.ui.screens.main.home.portfolio

import com.fiz.wisecrypto.ui.screens.main.models.ActiveUi

data class HomePortfolioViewState(
    val pricePortfolio: String = "",
    val totalReturn: String = "",
    val pricePortfolioIncreased: Boolean = true,
    val changePercentagePricePortfolio: String = "",
    val portfolio: List<ActiveUi> = emptyList(),
    val isLoading: Boolean = false
)