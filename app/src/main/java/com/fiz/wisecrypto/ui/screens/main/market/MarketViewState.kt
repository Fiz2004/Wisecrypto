package com.fiz.wisecrypto.ui.screens.main.market

import com.fiz.wisecrypto.data.data_source.coinsStore
import com.fiz.wisecrypto.domain.models.Coin

data class MarketViewState(
    val searchText: String = "",
    val selectedChipNumber: Int = 0,
    val coins: List<Coin> = coinsStore
)