package com.fiz.wisecrypto.ui.screens.main.market.detail_transaction_cash

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.components.Title
import com.fiz.wisecrypto.ui.components.WiseCryptoButton
import com.fiz.wisecrypto.ui.screens.main.components.MainColumn
import com.fiz.wisecrypto.ui.screens.main.components.PaymentItem
import com.fiz.wisecrypto.ui.screens.main.components.TransactionItem
import com.fiz.wisecrypto.ui.theme.bodyMedium2
import com.fiz.wisecrypto.util.showError

@Composable
fun MarketDetailTransactionCashScreen(
    viewModel: MarketDetailTransactionCashViewModel = viewModel(),
    currency: Double,
    commission: Double,
    moveReturn: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.onEvent(MarketDetailTransactionCashEvent.InitTransaction(currency, commission))
    }

    ReactEffect(viewModel, moveReturn)

    val viewState = viewModel.viewState

    Box {
        MainColumn(
            textToolbar = stringResource(R.string.detail_transaction_add_toolbar),
            onClickBackButton = { viewModel.onEvent(MarketDetailTransactionCashEvent.BackButtonClicked) }
        ) {

            LazyColumn(modifier = Modifier.padding(16.dp)) {
                item {
                    TransactionItem(
                        transaction = viewState.transaction
                    )
                }

                item {
                    Column {
                        Spacer(modifier = Modifier.height(24.dp))
                        Title(text = R.string.methods_cash)
                        PaymentItem(
                            payment = viewState.payment,
                            border = true
                        )
                    }
                }

            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .imePadding()
        ) {
            WiseCryptoButton(
                modifier = Modifier.fillMaxWidth(),
                text = R.string.detail_transaction_add_cancel,
                color = MaterialTheme.colorScheme.error,
                onClick = { viewModel.onEvent(MarketDetailTransactionCashEvent.CancelButtonClicked) }
            )
        }
    }

}

@Composable
fun OpenningText(
    titleText: String,
    infoText: String,
    isOpen: Boolean = false,
    onClickOpen: () -> Unit = {}
) {
    val idIcon = if (isOpen)
        R.drawable.ic_arrow_down
    else
        R.drawable.ic_arrow_right
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = titleText,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .size(16.dp)
                    .clickable {
                        onClickOpen()
                    },
                painter = painterResource(id = idIcon),
                contentDescription = null
            )
        }
        if (isOpen)
            Text(
                text = infoText,
                style = MaterialTheme.typography.bodyMedium2,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
    }
}

@Composable
private fun ReactEffect(
    viewModel: MarketDetailTransactionCashViewModel,
    moveReturn: () -> Unit,
) {
    val viewEffect = viewModel.viewEffect
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                MarketDetailTransactionCashViewEffect.MoveReturn -> moveReturn()
                is MarketDetailTransactionCashViewEffect.ShowError -> showError(
                    context,
                    effect.message
                )
            }
        }
    }
}