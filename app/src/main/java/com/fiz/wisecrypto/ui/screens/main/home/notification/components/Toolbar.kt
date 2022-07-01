package com.fiz.wisecrypto.ui.screens.main.home.notification.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R

@Composable
fun Toolbar(
    onClickBackButton: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = onClickBackButton) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.notification_ic_arrow_back),
                contentDescription = null,
                tint=MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = stringResource(R.string.notification_notifications),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

    }

    Spacer(modifier = Modifier.height(16.dp))

}