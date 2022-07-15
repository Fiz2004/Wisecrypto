package com.fiz.wisecrypto.ui.screens.main.home.main

import com.fiz.wisecrypto.domain.models.Coin
import com.fiz.wisecrypto.ui.screens.main.models.ActiveUi

data class HomeViewState(
    val fullName: String = "",
    val photo: String = "",
    val watchlist: List<Coin>? = null,
    val pricePortfolio: String = "",
    val balance: String = "",
    val pricePortfolioIncreased: Boolean = true,
    val changePercentageBalance: String = "",
    val actives: List<ActiveUi>? = null,
    val isLoading: Boolean = false
)