package com.fiz.wisecrypto.ui.screens.main.market.main

import com.fiz.wisecrypto.ui.screens.main.models.CoinUi
import java.io.Serializable

data class MarketViewState(
    val searchText: String = "",
    val selectedChipNumber: Int = 0,
    @Transient
    val coins: List<CoinUi>? = null,
    val isLoading: Boolean = false
) : Serializable