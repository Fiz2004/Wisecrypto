package com.fiz.wisecrypto.ui.screens.main.market.add_balance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.components.Title
import com.fiz.wisecrypto.ui.components.WiseCryptoButton
import com.fiz.wisecrypto.ui.screens.main.components.BalanceInfo
import com.fiz.wisecrypto.ui.screens.main.components.MainColumn
import com.fiz.wisecrypto.ui.screens.main.components.PaymentItem
import com.fiz.wisecrypto.ui.screens.main.market.components.DetailTransaction
import com.fiz.wisecrypto.ui.screens.main.market.components.NominalTextField
import com.fiz.wisecrypto.ui.util.LifeCycleEffect
import com.fiz.wisecrypto.util.showError

@Composable
fun MarketAddBalanceScreen(
    viewModel: MarketAddBalanceViewModel = viewModel(),
    moveReturn: () -> Unit,
    moveMarketDetailTransactionAddScreen: (Double, Double) -> Unit
) {
    LifeCycleEffect(viewModel)
    ReactEffect(viewModel, moveReturn, moveMarketDetailTransactionAddScreen)

    val viewState = viewModel.viewState

    Box {
        MainColumn(
            textToolbar = stringResource(R.string.add_balance_toolbar),
            onClickBackButton = { viewModel.onEvent(MarketAddBalanceEvent.BackButtonClicked) }
        ) {
            BalanceInfo(
                valueBalance = viewState.valueBalance,
                modifier = Modifier.fillMaxWidth(),
            )

            LazyColumn {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    NominalTextField(
                        symbolCurrency = viewState.symbolCurrency,
                        currencyForBuy = viewState.currencyForBuy,
                        onValueChange = {
                            viewModel.onEvent(
                                MarketAddBalanceEvent.ValueCurrencyChanged(it)
                            )
                        })
                }
                item {
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))
                        Title(text = R.string.methods_payments)
                        PaymentItem(
                            payment = viewState.payment,
                            iconId = R.drawable.ic_arrow_right,
                            colorIcon = MaterialTheme.colorScheme.onSurfaceVariant,
                            actionOnClick = {
                                viewModel.onEvent(
                                    MarketAddBalanceEvent.PaymentClicked
                                )
                            }
                        )
                    }
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
                text = R.string.add_balance_pay,
                color = MaterialTheme.colorScheme.primary,
                onClick = { viewModel.onEvent(MarketAddBalanceEvent.PayButtonClicked) }
            )
        }
    }

}

@Composable
private fun ReactEffect(
    viewModel: MarketAddBalanceViewModel,
    moveReturn: () -> Unit,
    moveMarketDetailTransactionAddScreen: (Double, Double) -> Unit,
) {
    val viewEffect = viewModel.viewEffect
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                MarketAddBalanceViewEffect.MoveReturn -> moveReturn()
                is MarketAddBalanceViewEffect.ShowError -> showError(context, effect.message)
                is MarketAddBalanceViewEffect.MoveMarketDetailTransactionAddScreen -> moveMarketDetailTransactionAddScreen(
                    effect.currency,
                    effect.commission
                )
            }
        }
    }
}