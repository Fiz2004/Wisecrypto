package com.fiz.wisecrypto.ui.screens.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.ui.components.IconCoin
import com.fiz.wisecrypto.ui.screens.main.home.main.components.RelativeLabel
import com.fiz.wisecrypto.ui.screens.main.models.CoinUi
import com.fiz.wisecrypto.ui.theme.MulishBold
import com.fiz.wisecrypto.ui.theme.MulishRegular

@Composable
fun CoinItem(coinUi: CoinUi, moveHomeDetailScreen: (String) -> Unit) {

    Row(
        modifier = Modifier
            .height(88.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colorScheme.onPrimary)
            .clickable { moveHomeDetailScreen(coinUi.id) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconCoin(iconUrl = coinUi.icon)
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = coinUi.abbreviated.uppercase(),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = coinUi.name,
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
                text = coinUi.cost,
                style = MaterialTheme.typography.displayMedium.copy(fontFamily = MulishBold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            RelativeLabel(Increased = coinUi.up, value = coinUi.value)
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}