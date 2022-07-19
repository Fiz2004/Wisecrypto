package com.fiz.wisecrypto.ui.screens.main.market.add_balance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.fiz.wisecrypto.ui.components.TextFieldWithHeader
import com.fiz.wisecrypto.ui.components.Title
import com.fiz.wisecrypto.ui.components.WiseCryptoButton
import com.fiz.wisecrypto.ui.screens.main.components.BalanceInfo
import com.fiz.wisecrypto.ui.screens.main.components.MainColumn
import com.fiz.wisecrypto.ui.screens.main.components.PaymentItem
import com.fiz.wisecrypto.ui.theme.bodyMedium2
import com.fiz.wisecrypto.ui.theme.hint
import com.fiz.wisecrypto.ui.theme.titleMedium3
import com.fiz.wisecrypto.ui.util.LifeCycleEffect
import com.fiz.wisecrypto.util.showError

@Composable
fun MarketAddBalanceScreen(
    viewModel: MarketAddBalanceViewModel = viewModel(),
    state: LazyListState = rememberLazyListState(),
    moveReturn: () -> Unit,
    moveMarketAddBalanceScreen: () -> Unit = {}
) {
    LifeCycleEffect(viewModel)
    ReactEffect(viewModel, moveReturn, moveMarketAddBalanceScreen)

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

            LazyColumn(state = state) {
                item {
                    Column {
                        Spacer(modifier = Modifier.height(24.dp))
                        TextFieldWithHeader(
                            textHeader = stringResource(
                                id = R.string.add_balance_title_nominal,
                            ),
                            text = stringResource(
                                id = R.string.buy_one_currency,
                                viewState.symbolCurrency,
                                viewState.currencyForBuy
                            ),
                            onValueChange = {
                                viewModel.onEvent(
                                    MarketAddBalanceEvent.ValueCurrencyChanged(
                                        it
                                    )
                                )
                            },
                            textHint = stringResource(
                                id = R.string.buy_one_currency,
                                viewState.symbolCurrency,
                                viewState.currencyForBuy
                            )
                        )
                    }
                }
                item {
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))
                        Title(text = R.string.add_balance_payments)
                        PaymentItem(
                            payment = viewState.payment,
                            iconId = R.drawable.profile_ic_arrow_right,
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
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.add_balance_detail_transaction_title),
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
                                        viewState.currencyForBuy
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
                                        viewState.commission
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
                                        viewState.total
                                    ),
                                    style = MaterialTheme.typography.titleMedium3,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
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
    moveMarketAddBalanceScreen: () -> Unit
) {
    val viewEffect = viewModel.viewEffect
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                MarketAddBalanceViewEffect.MoveReturn -> moveReturn()
                is MarketAddBalanceViewEffect.ShowError -> showError(context, effect.message)
                MarketAddBalanceViewEffect.AddBalanceClicked -> moveMarketAddBalanceScreen()
            }
        }
    }
}