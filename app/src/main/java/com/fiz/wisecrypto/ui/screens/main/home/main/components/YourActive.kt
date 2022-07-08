package com.fiz.wisecrypto.ui.screens.main.home.main.components

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
fun YourActive(portfolio: List<ActiveUi>) {
    TitleYourActive()
    YourActiveRow(portfolio)
}

@Composable
private fun TitleYourActive() {
    Row {
        Text(
            text = stringResource(R.string.home_your_active),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.home_see_all),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun YourActiveRow(portfolio: List<ActiveUi>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        portfolio.forEach {
            item {
                YourActiveItem(active = it)
            }
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
}