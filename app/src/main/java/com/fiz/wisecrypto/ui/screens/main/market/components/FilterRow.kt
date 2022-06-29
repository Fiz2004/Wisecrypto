package com.fiz.wisecrypto.ui.screens.main.market.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyScopeMarker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.ui.screens.main.market.MarketEvent
import com.fiz.wisecrypto.ui.screens.main.market.MarketViewModel

@OptIn(ExperimentalMaterial3Api::class)
@LazyScopeMarker
fun LazyListScope.filterRow(
    indexSelected: Int,
    viewModel: MarketViewModel
) {
    item {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            marketChip(
                text = "Избранное",
                selected = indexSelected == 0,
                onClick = { viewModel.onEvent(MarketEvent.MarketChipClicked(0)) }
            )
            marketChip(
                text = "Рынки FIAT",
                selected = indexSelected == 1,
                onClick = { viewModel.onEvent(MarketEvent.MarketChipClicked(1)) })
            marketChip(
                text = "Рынки ETF",
                selected = indexSelected == 2,
                onClick = { viewModel.onEvent(MarketEvent.MarketChipClicked(2)) })
            marketChip(
                text = "Рынки BNB",
                selected = indexSelected == 3,
                onClick = { viewModel.onEvent(MarketEvent.MarketChipClicked(3)) })
            marketChip(
                text = "Рынки BTC",
                selected = indexSelected == 4,
                onClick = { viewModel.onEvent(MarketEvent.MarketChipClicked(4)) })
            marketChip(
                text = "Рынки ALTS",
                selected = indexSelected == 5,
                onClick = { viewModel.onEvent(MarketEvent.MarketChipClicked(5)) })
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}