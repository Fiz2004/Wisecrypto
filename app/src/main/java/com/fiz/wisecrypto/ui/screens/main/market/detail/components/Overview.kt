package com.fiz.wisecrypto.ui.screens.main.market.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R

@Composable
fun Overview(
    allTimeHigh: String,
    allTimeLow: String,
    marketCap: String,
    totalVolume: String,
    totalSupply: String,
    maxSupply: String,
    marketCapChange: String,
    totalVolumeChange: String,

    ) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.detail_overview),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))
        Divider(modifier = Modifier.fillMaxWidth(), thickness = 0.3.dp)
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Column(modifier = Modifier.weight(0.5f)) {
                OverviewItem(stringResource(R.string.detail_all_time_high), allTimeHigh)
                Spacer(modifier = Modifier.height(16.dp))
                OverviewItem(stringResource(R.string.detail_all_time_low), allTimeLow)
                Spacer(modifier = Modifier.height(16.dp))
                OverviewItem(stringResource(R.string.detail_market_cap), marketCap)
                Spacer(modifier = Modifier.height(16.dp))
                OverviewItem(stringResource(R.string.detail_total_volume), totalVolume)
            }
            Spacer(modifier = Modifier.width(24.dp))
            Column(modifier = Modifier.weight(0.5f)) {
                OverviewItem(stringResource(R.string.detail_total_supply), totalSupply)
                Spacer(modifier = Modifier.height(16.dp))
                OverviewItem(stringResource(R.string.detail_max_supply), maxSupply)
                Spacer(modifier = Modifier.height(16.dp))
                OverviewItem(stringResource(R.string.detail_market_cap_change), marketCapChange)
                Spacer(modifier = Modifier.height(16.dp))
                OverviewItem(stringResource(R.string.detail_total_volume_change), totalVolumeChange)
            }
        }
    }
}

@Composable
fun OverviewItem(text: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}