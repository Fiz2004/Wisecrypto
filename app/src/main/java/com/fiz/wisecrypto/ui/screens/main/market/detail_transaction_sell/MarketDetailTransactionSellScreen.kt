package com.fiz.wisecrypto.ui.screens.main.market.detail_transaction_sell

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.components.MainColumn
import com.fiz.wisecrypto.ui.screens.main.components.TransactionItem
import com.fiz.wisecrypto.ui.screens.main.market.components.DetailTransaction
import com.fiz.wisecrypto.util.showError

@Composable
fun MarketDetailTransactionSellScreen(
    viewModel: MarketDetailTransactionSellViewModel = viewModel(),
    idCoin: String,
    userCoinForSell: Double,
    priceCurrency: Double,
    moveReturn: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.onEvent(
            MarketDetailTransactionSellEvent.InitTransaction(
                idCoin,
                userCoinForSell,
                priceCurrency
            )
        )
    }

    ReactEffect(viewModel, moveReturn)

    val viewState = viewModel.viewState

    Box {
        MainColumn(
            textToolbar = stringResource(R.string.detail_transaction_add_toolbar),
            onClickBackButton = { viewModel.onEvent(MarketDetailTransactionSellEvent.BackButtonClicked) }
        ) {

            LazyColumn(modifier = Modifier.padding(16.dp)) {
                item {
                    TransactionItem(
                        transaction = viewState.transaction
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    DetailTransaction(
                        currencyForBuy = viewState.currencyForBuy,
                        commission = viewState.commission,
                        total = viewState.total,
                    )
                }
            }
        }
    }

}

@Composable
private fun ReactEffect(
    viewModel: MarketDetailTransactionSellViewModel,
    moveReturn: () -> Unit,
) {
    val viewEffect = viewModel.viewEffect
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                MarketDetailTransactionSellViewEffect.MoveReturn -> moveReturn()
                is MarketDetailTransactionSellViewEffect.ShowError -> showError(
                    context,
                    effect.message
                )
            }
        }
    }
}