package com.fiz.wisecrypto.ui.screens.splash.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.theme.White

@Composable
fun TextSlogan() {
    Text(
        text = stringResource(R.string.slogan),
        color = White,
        style = MaterialTheme.typography.displayMedium
    )
}