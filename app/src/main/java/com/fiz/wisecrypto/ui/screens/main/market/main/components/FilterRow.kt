package com.fiz.wisecrypto.ui.screens.main.market.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FilterRow(
    texts: List<String>,
    indexSelected: Int,
    onClick: (Int) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        texts.forEachIndexed { index, text ->
            item {
                MarketChip(
                    text = text,
                    selected = indexSelected == index,
                    onClick = { onClick(index) }
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}