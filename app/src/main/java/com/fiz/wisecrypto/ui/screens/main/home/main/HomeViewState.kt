package com.fiz.wisecrypto.ui.screens.main.home.main

import com.fiz.wisecrypto.data.data_source.coinsStore
import com.fiz.wisecrypto.domain.models.Coin

data class HomeViewState(
    val fullName: String = "",
    val photo: String = "",
    val coins: List<Coin> = coinsStore,
    val isLoading: Boolean = false
)