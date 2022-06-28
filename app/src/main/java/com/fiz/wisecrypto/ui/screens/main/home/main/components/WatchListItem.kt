package com.fiz.wisecrypto.ui.screens.main.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.ui.theme.MulishBold
import com.fiz.wisecrypto.ui.theme.MulishRegular

@Composable
fun WatchListItem(
    icon: Int,
    abbreviated: String,
    name: String,
    cost: String,
    up: Boolean,
    value: String
) {
    Row(
        modifier = Modifier
            .height(88.dp)
            .background(
                color = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .size(40.dp),
                painter = painterResource(id = icon),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = abbreviated,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.displayMedium.copy(
                    fontFamily = MulishRegular
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = cost,
                style = MaterialTheme.typography.displayMedium.copy(fontFamily = MulishBold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            RelativeLabel(up = up, value = value)
        }
    }
}