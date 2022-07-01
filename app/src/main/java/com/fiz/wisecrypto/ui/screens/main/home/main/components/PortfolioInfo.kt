package com.fiz.wisecrypto.ui.screens.main.home.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R
import kotlin.math.abs

@Composable
fun PortfolioInfo(balance: String, changePercentageBalance: Double) {

    val icon = if (changePercentageBalance > 0.0)
        R.drawable.home_ic_up_right
    else
        R.drawable.home_ic_down_left

    val color = if (changePercentageBalance > 0.0)
        MaterialTheme.colorScheme.primary
    else
        MaterialTheme.colorScheme.secondary


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(10.dp)
            ),
        contentAlignment = Alignment.TopStart
    ) {
        Image(
            painterResource(id = R.drawable.home_pic_total_background),
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .align(Alignment.BottomCenter),
            contentDescription = null
        )
        Row(
            modifier = Modifier.padding(
                vertical = 26.dp,
                horizontal = 24.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(R.string.home_total_portfolio),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = balance,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = icon),
                    tint = color,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${"%.1f".format(abs(changePercentageBalance))}%",
                    style = MaterialTheme.typography.bodyMedium,
                    color = color
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
}