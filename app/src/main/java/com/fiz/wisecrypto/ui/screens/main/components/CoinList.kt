package com.fiz.wisecrypto.ui.screens.main.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.domain.models.Coin
import com.fiz.wisecrypto.ui.screens.main.home.main.components.WatchListItem

@Composable
fun CoinItem(coin: Coin) {
    WatchListItem(
        coinUi = coin.toCoinUi()
    )
    Spacer(modifier = Modifier.height(8.dp))
}


