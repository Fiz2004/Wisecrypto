package com.fiz.wisecrypto.ui.screens.main.home.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.theme.hint

@Composable
fun UserInfo(icon: Int, fullName: String, onClickIconButton: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(40.dp)
                .clip(shape = RoundedCornerShape(20.dp)),
            painter = painterResource(id = icon),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = stringResource(R.string.home_hello),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.hint
            )
            Text(
                text = fullName,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onClickIconButton) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.home_ic_notification),
                contentDescription = null
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}