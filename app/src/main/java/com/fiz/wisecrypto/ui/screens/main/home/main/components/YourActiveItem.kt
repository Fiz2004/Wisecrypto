package com.fiz.wisecrypto.ui.screens.main.home.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.components.IconCoin
import com.fiz.wisecrypto.ui.screens.main.models.ActiveUi
import com.fiz.wisecrypto.ui.theme.hint
import com.fiz.wisecrypto.ui.theme.titleMedium3

@Composable
fun YourActiveItem(
    active: ActiveUi,
    moveHomeDetailScreen: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colorScheme.onPrimary)
            .clickable { moveHomeDetailScreen(active.id) }
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconCoin(iconUrl = active.icon)

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = active.abbreviated,
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = active.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(24.dp))

            RelativeLabel(
                Increased = active.pricePortfolioIncreased,
                value = active.changePercentage
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Divider(
                modifier = Modifier.weight(1f),
                thickness = 0.3.dp,
                color = MaterialTheme.colorScheme.hint
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(R.string.home_portfolio),
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal),
                    color = MaterialTheme.colorScheme.hint
                )
                Text(
                    text = active.portfolio,
                    style = MaterialTheme.typography.titleMedium3,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.align(Alignment.Bottom),
                text = active.equivalent,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}