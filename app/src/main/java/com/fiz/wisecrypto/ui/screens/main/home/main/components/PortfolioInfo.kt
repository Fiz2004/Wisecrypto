package com.fiz.wisecrypto.ui.screens.main.home.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.home.components.BigRelativeLabel

@Composable
fun PortfolioInfo(
    balancePortfolio: String,
    isPricePortfolioIncreased: Boolean,
    percentageChangedBalance: String,
    balanceCurrency: String
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        item {
            Box(
                modifier = Modifier
                    .width(345.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                    ),
                contentAlignment = Alignment.TopStart
            ) {
                Image(
                    painterResource(id = R.drawable.home_pic_total_background),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .align(Alignment.BottomCenter),
                    contentScale = ContentScale.Crop,
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
                            text = balancePortfolio,
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    if (percentageChangedBalance != "0.0%")
                        BigRelativeLabel(
                            inverse = true,
                            increased = isPricePortfolioIncreased,
                            value = percentageChangedBalance
                        )
                }
            }
        }

        item {
            Box(
                modifier = Modifier
                    .width(345.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(
                        color = MaterialTheme.colorScheme.onPrimary,
                    ),
                contentAlignment = Alignment.TopStart
            ) {
                Row(
                    modifier = Modifier.padding(
                        vertical = 26.dp,
                        horizontal = 24.dp
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = stringResource(R.string.home_your_balance),
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "$$balanceCurrency",
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.home_add_balance),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
}

