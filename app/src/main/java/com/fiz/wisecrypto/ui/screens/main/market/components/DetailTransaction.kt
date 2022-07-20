package com.fiz.wisecrypto.ui.screens.main.market.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.theme.bodyMedium2
import com.fiz.wisecrypto.ui.theme.hint
import com.fiz.wisecrypto.ui.theme.titleMedium3

@Composable
fun DetailTransaction(
    currencyForBuy: String,
    commission: String,
    total: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.detail_transaction_title),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row {
                Text(
                    text = stringResource(R.string.add_balance_price),
                    style = MaterialTheme.typography.bodyMedium2,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(
                        R.string.add_balance_price_value,
                        currencyForBuy
                    ),
                    style = MaterialTheme.typography.bodyMedium2,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(
                    text = stringResource(R.string.add_balance_сommission),
                    style = MaterialTheme.typography.bodyMedium2,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(
                        R.string.add_balance_price_value,
                        commission
                    ),
                    style = MaterialTheme.typography.bodyMedium2,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.hint
                )
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.add_balance_plus),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.hint
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(
                    text = stringResource(R.string.add_balance_total),
                    style = MaterialTheme.typography.titleMedium3,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(
                        R.string.add_balance_price_value,
                        total
                    ),
                    style = MaterialTheme.typography.titleMedium3,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}