package com.fiz.wisecrypto.ui.screens.main.profile.list_transactions.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.domain.models.StatusTransaction
import com.fiz.wisecrypto.domain.models.Transaction
import com.fiz.wisecrypto.domain.models.TypeTransaction
import com.fiz.wisecrypto.ui.theme.*
import org.threeten.bp.format.DateTimeFormatter


@Composable
fun TransactionList(transactions: List<Transaction>) {
    LazyColumn {

        transactions.forEach {
            item {
                TransactionItem(transaction = it)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

    }
}

@Composable
private fun TransactionItem(
    transaction: Transaction
) {

    val icon = when (transaction.status) {
        StatusTransaction.Success -> R.drawable.notification_ic_ok
        StatusTransaction.Process -> R.drawable.notification_ic_time
        StatusTransaction.Fail -> R.drawable.notification_ic_cancel
    }

    val color = when (transaction.status) {
        StatusTransaction.Success -> LightGreen2
        StatusTransaction.Process -> LightYellow2
        StatusTransaction.Fail -> LightRed2
    }

    val colorText = when (transaction.status) {
        StatusTransaction.Success -> DarkGreen
        StatusTransaction.Process -> Warning
        StatusTransaction.Fail -> Red
    }

    val textType = when (transaction.status) {
        StatusTransaction.Success -> stringResource(R.string.list_transactions_success)
        StatusTransaction.Process -> stringResource(R.string.list_transactions_pending)
        StatusTransaction.Fail -> stringResource(R.string.list_transactions_fail)
    }

    val title = when (transaction.type) {
        is TypeTransaction.Balance -> stringResource(R.string.list_transactions_add_balance)
        is TypeTransaction.Buy -> stringResource(R.string.list_transactions_sell)
        is TypeTransaction.Sell -> stringResource(R.string.list_transactions_buy)
    }
    val description = transaction.textDescription

    val data =
        DateTimeFormatter.ofPattern("dd MMM \nHH:mm")
            .format(transaction.data)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 16.dp, vertical = 16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(
                    id = R.string.list_transactions_transaction_code,
                    transaction.id
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .clip(
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(color)
                    .padding(horizontal = 8.dp)
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = textType,
                    style = MaterialTheme.typography.bodyMedium2,
                    color = colorText
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.3.dp,
            color = MaterialTheme.colorScheme.hint
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(color = color),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(20.dp),
                    painter = painterResource(id = icon),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = data.split("\n")[0],
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = data.split("\n")[1],
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }


    }
}