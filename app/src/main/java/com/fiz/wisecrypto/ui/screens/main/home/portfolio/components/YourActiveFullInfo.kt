package com.fiz.wisecrypto.ui.screens.main.home.portfolio.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.ui.screens.main.models.ActiveUi

@Composable
fun YourActiveFullInfo(portfolio: List<ActiveUi>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        portfolio.forEach {
            item {
                YourActiveItemFullInfo(active = it)
            }
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
}