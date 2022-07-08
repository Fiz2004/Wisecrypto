package com.fiz.wisecrypto.ui.screens.splash.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.ui.components.Progress

@Composable
fun ProgressOrSpacer(isLoading: Boolean, modifier: Modifier = Modifier) {
    if (isLoading)
        Progress(modifier = modifier)
    else
        Spacer(modifier = Modifier.height(40.dp))
}