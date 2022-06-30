package com.fiz.wisecrypto.ui.screens.main.home.main

import com.fiz.wisecrypto.domain.models.Coin
import com.fiz.wisecrypto.ui.screens.main.models.ActiveUi

data class HomeViewState(
    val fullName: String = "",
    val photo: String = "",
    val coins: List<Coin> = emptyList(),
    val balance: String = "",
    val changePercentageBalance: Double = 0.0,
    val portfolio: List<ActiveUi> = emptyList(),
    val isLoading: Boolean = false
)