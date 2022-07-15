package com.fiz.wisecrypto.ui.screens.main.home.main.components

import androidx.compose.runtime.Composable
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.domain.models.Coin
import com.fiz.wisecrypto.ui.components.Title
import com.fiz.wisecrypto.ui.screens.main.components.CoinColumn

@Composable
fun WatchList(coins: List<Coin>, moveHomeDetailScreen: (String) -> Unit) {
    Title(R.string.home_watchlist)
    CoinColumn(
        coins,
        moveHomeDetailScreen = moveHomeDetailScreen
    )
}