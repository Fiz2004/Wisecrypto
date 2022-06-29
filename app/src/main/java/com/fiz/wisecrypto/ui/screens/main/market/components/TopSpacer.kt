package com.fiz.wisecrypto.ui.screens.main.market.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyScopeMarker
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@LazyScopeMarker
fun LazyListScope.topSpacer() {
    item {
        Spacer(
            modifier = Modifier
                .windowInsetsTopHeight(WindowInsets.statusBars)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}