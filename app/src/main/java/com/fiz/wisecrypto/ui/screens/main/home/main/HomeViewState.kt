package com.fiz.wisecrypto.ui.screens.main.home.main

import com.fiz.wisecrypto.domain.models.Coin
import com.fiz.wisecrypto.ui.screens.main.models.ActiveUi

data class HomeViewState(
    val fullName: String = "",
    val photo: String = "",
    val watchlist: List<Coin>? = null,
    val balancePortfolio: String = "",
    val balanceCurrency: String = "",
    val isPricePortfolioIncreased: Boolean = true,
    val percentageChangedBalance: String = "",
    val actives: List<ActiveUi>? = null,
    val isLoading: Boolean = false
)