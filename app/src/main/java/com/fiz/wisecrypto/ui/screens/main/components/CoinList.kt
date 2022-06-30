package com.fiz.wisecrypto.ui.screens.main.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyScopeMarker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.domain.models.Coin
import com.fiz.wisecrypto.ui.screens.main.home.main.components.WatchListItem

@OptIn(ExperimentalMaterial3Api::class)
@LazyScopeMarker
fun LazyListScope.coinList(coins: List<Coin>) {
    coins.forEach {
        item {
            WatchListItem(
                coinUi = it.toCoinUi()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


