package com.fiz.wisecrypto.ui.screens.main.home.main

import com.fiz.wisecrypto.ui.screens.main.models.ActiveUi
import com.fiz.wisecrypto.ui.screens.main.models.CoinUi

data class HomeViewState(
    val fullName: String = "",
    val photo: String = "",
    val watchlist: List<CoinUi> = emptyList(),
    val balancePortfolio: String = "",
    val balanceCurrency: String = "",
    val isPricePortfolioIncreased: Boolean = true,
    val percentageChangedBalance: String = "",
    val actives: List<ActiveUi> = emptyList(),
    val isLoading: Boolean = true,
    val isFirstLoading: Boolean = true
)