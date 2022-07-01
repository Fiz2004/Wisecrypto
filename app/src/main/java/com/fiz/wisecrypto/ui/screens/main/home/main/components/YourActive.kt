package com.fiz.wisecrypto.ui.screens.main.home.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.ui.screens.main.models.ActiveUi

@Composable
fun YourActive(portfolio: List<ActiveUi>) {
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