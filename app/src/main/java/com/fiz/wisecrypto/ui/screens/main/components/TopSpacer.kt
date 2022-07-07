package com.fiz.wisecrypto.ui.screens.main.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopSpacer() {
    Spacer(
        modifier = Modifier
            .windowInsetsTopHeight(WindowInsets.statusBars)
    )
    Spacer(modifier = Modifier.height(16.dp))
}