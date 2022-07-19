package com.fiz.wisecrypto.ui.screens.main.market.buy

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.fiz.wisecrypto.ui.screens.main.components.MainColumn
import com.fiz.wisecrypto.ui.screens.main.market.components.ActionButton
import com.fiz.wisecrypto.ui.theme.Gray3
import com.fiz.wisecrypto.ui.theme.Gray4
import com.fiz.wisecrypto.ui.theme.bodyMedium2
import com.fiz.wisecrypto.ui.util.LifeCycleEffect
import com.fiz.wisecrypto.util.showError

@Composable
fun MarketBuyScreen(
    viewModel: MarketBuyViewModel = viewModel(),
    state: LazyListState = rememberLazyListState(),
    moveReturn: () -> Unit,
    moveAddBalanceScreen: () -> Unit = {}
) {
    LifeCycleEffect(viewModel)
    ReactEffect(viewModel, moveReturn, moveAddBalanceScreen)

    val viewState = viewModel.viewState

    Box {
        MainColumn(
            textToolbar = stringResource(R.string.buy_toolbar),
            onClickBackButton = { viewModel.onEvent(MarketBuyEvent.BackButtonClicked) }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(112.dp)
                    .background(color = MaterialTheme.colorScheme.onPrimary)
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.background_balance),
                    contentDescription = null
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.onPrimary,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(
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
                            text = "$${viewState.valueBalance}",
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    ActionButton(
                        text = R.string.buy_add,
                        onClick = { viewModel.onEvent(MarketBuyEvent.AddBalanceClicked) }
                    )
                }
            }

            LazyColumn(state = state) {
                item {
                    Column {
                        Spacer(modifier = Modifier.height(24.dp))
                        TextFieldWithHeader(
                            textHeader = stringResource(
                                id = R.string.buy_one_currency_title,
                                viewState.nameCurrency
                            ),
                            text = stringResource(
                                id = R.string.buy_one_currency,
                                viewState.symbolCurrency,
                                viewState.currencyForBuy
                            ),
                            onValueChange = {
                                viewModel.onEvent(
                                    MarketBuyEvent.ValueCurrencyChanged(
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
                        Spacer(modifier = Modifier.height(18.dp))
                        Title(text = R.string.buy_one_coin_title, viewState.nameCoin)
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = Gray3,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(vertical = 12.dp)
                                .padding(horizontal = 16.dp),
                            text = stringResource(
                                id = R.string.sell_one_coin,
                                viewState.valueCoin,
                                viewState.symbolCoin
                            ),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Gray4,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(24.dp),
                            painter = painterResource(id = R.drawable.sell_alert),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = stringResource(R.string.buy_alert),
                            style = MaterialTheme.typography.bodyMedium2,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
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
                text = R.string.buy_buy,
                color = MaterialTheme.colorScheme.primary,
                onClick = { viewModel.onEvent(MarketBuyEvent.BuyButtonClicked) }
            )
        }
    }

}

@Composable
private fun ReactEffect(
    viewModel: MarketBuyViewModel,
    moveReturn: () -> Unit,
    moveAddBalanceScreen: () -> Unit
) {
    val viewEffect = viewModel.viewEffect
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                MarketBuyViewEffect.MoveReturn -> moveReturn()
                is MarketBuyViewEffect.ShowError -> showError(context, effect.message)
                MarketBuyViewEffect.AddBalanceClicked -> moveAddBalanceScreen()
            }
        }
    }
}