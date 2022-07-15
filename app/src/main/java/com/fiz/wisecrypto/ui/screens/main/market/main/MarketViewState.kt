package com.fiz.wisecrypto.ui.screens.main.market.main

import com.fiz.wisecrypto.domain.models.Coin

data class MarketViewState(
    val searchText: String = "",
    val selectedChipNumber: Int = 0,
    val coins: List<Coin>? = null,
    val isLoading: Boolean = false
)