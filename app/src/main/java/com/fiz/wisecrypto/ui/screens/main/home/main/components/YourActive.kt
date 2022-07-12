package com.fiz.wisecrypto.ui.screens.main.home.main.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.models.ActiveUi

@Composable
fun YourActive(
    portfolio: List<ActiveUi>,
    moveHomePortfolioScreen: () -> Unit,
    moveHomeDetailScreen: (String) -> Unit
) {
    TitleYourActive(moveHomePortfolioScreen)
    YourActiveRow(portfolio, moveHomeDetailScreen)
}

@Composable
private fun TitleYourActive(moveHomePortfolioScreen: () -> Unit) {
    Row {
        Text(
            text = stringResource(R.string.home_your_active),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.clickable { moveHomePortfolioScreen() },
            text = stringResource(R.string.home_see_all),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun YourActiveRow(portfolio: List<ActiveUi>, moveHomeDetailScreen: (String) -> Unit) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        portfolio.forEach {
            item {
                YourActiveItem(active = it, moveHomeDetailScreen)
            }
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
}