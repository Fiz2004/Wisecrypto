package com.fiz.wisecrypto.ui.screens.login.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TitleAndGreeting(textTitle: Int, textGreeting: Int) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = stringResource(textTitle),
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.displayLarge
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = stringResource(textGreeting),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.displaySmall
    )

    Spacer(modifier = Modifier.height(32.dp))
}