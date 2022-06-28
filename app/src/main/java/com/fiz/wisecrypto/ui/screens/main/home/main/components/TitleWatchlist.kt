package com.fiz.wisecrypto.ui.screens.main.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.fiz.wisecrypto.R

@Composable
fun TitleWatchlist() {
    Row {
        Text(
            text = stringResource(R.string.home_watchlist),
            style = MaterialTheme.typography.titleMedium
        )
    }
}