package com.fiz.wisecrypto.ui.screens.main.home.main.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.domain.models.Coin
import com.fiz.wisecrypto.ui.screens.main.components.CoinColumn

@Composable
fun Watchlist(coins: List<Coin>, moveHomeDetailScreen: (String) -> Unit) {
    TitleWatchlist()
    CoinColumn(coins, moveHomeDetailScreen)
}


@Composable
private fun TitleWatchlist() {
    Row {
        Text(
            text = stringResource(R.string.home_watchlist),
            style = MaterialTheme.typography.titleMedium
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}