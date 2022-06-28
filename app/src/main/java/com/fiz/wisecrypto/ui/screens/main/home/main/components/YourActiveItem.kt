package com.fiz.wisecrypto.ui.screens.main.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.theme.MulishBold
import com.fiz.wisecrypto.ui.theme.hint

@Composable
fun YourActiveItem(
    icon: Int,
    abbreviated: String,
    name: String,
    portfolio: String,
    equivalent: String,
    up: Boolean,
    value: String
) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .background(
                color = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color = MaterialTheme.colorScheme.surface)
                    .clip(shape = RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(28.dp),
                    painter = painterResource(id = icon),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = abbreviated,
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(24.dp))

            RelativeLabel(up = up, value = value)
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
                    text = portfolio,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = MulishBold,
                        //                                    fontWeight = FontWeight.Bold,
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.align(Alignment.Bottom),
                text = equivalent,
                style = MaterialTheme.typography.bodySmall.copy(fontFamily = MulishBold),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}