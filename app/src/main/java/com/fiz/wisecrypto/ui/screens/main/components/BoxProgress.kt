package com.fiz.wisecrypto.ui.screens.main.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fiz.wisecrypto.ui.components.Progress

@Composable
fun BoxProgress(isLoading: Boolean) {
    if (isLoading)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Progress()
        }
}