package com.fiz.wisecrypto.ui.screens.main.home.portfolio

import com.fiz.wisecrypto.ui.screens.main.models.ActiveUi

data class HomePortfolioViewState(
    val balancePortfolio: String = "",
    val totalReturn: String = "",
    val isPricePortfolioIncreased: Boolean = true,
    val percentageChangedBalance: String = "",
    val portfolio: List<ActiveUi>? = null,
    val isLoading: Boolean = false
)