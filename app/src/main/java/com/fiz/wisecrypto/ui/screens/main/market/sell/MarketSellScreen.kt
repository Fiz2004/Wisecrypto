package com.fiz.wisecrypto.ui.screens.main.market.sell

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.components.IconCoin
import com.fiz.wisecrypto.ui.components.TextFieldWithHeader
import com.fiz.wisecrypto.ui.components.Title
import com.fiz.wisecrypto.ui.components.WiseCryptoButton
import com.fiz.wisecrypto.ui.screens.main.components.MainColumn
import com.fiz.wisecrypto.ui.theme.Gray3
import com.fiz.wisecrypto.ui.theme.Gray4
import com.fiz.wisecrypto.ui.theme.bodyMedium2
import com.fiz.wisecrypto.ui.util.LifeCycleEffect
import com.fiz.wisecrypto.ui.util.showError

@Composable
fun MarketSellScreen(
    viewModel: MarketSellViewModel = viewModel(),
    state: LazyListState = rememberLazyListState(),
    moveReturn: () -> Unit,
) {
    LifeCycleEffect(viewModel)
    ReactEffect(viewModel, moveReturn)

    val viewState = viewModel.viewState

    Box {
        MainColumn(
            textToolbar = stringResource(R.string.sell_toolbar),
            onClickBackButton = { viewModel.onEvent(MarketSellEvent.BackButtonClicked) }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconCoin(iconUrl = viewState.icon)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = viewState.valueActiveCoin + " " + viewState.symbolCoin,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.weight(1f))
                SellAllButton(
                    text = R.string.sell_sell_all,
                    onClick = { viewModel.onEvent(MarketSellEvent.SellAllButtonClicked) }
                )
            }

            LazyColumn(state = state) {
                item {
                    Column {
                        Spacer(modifier = Modifier.height(24.dp))
                        TextFieldWithHeader(
                            textHeader = stringResource(
                                id = R.string.sell_one_coin_title,
                                viewState.nameCoin
                            ),
                            text = stringResource(
                                id = R.string.sell_one_coin,
                                viewState.coinForSell,
                                viewState.symbolCoin
                            ),
                            onValueChange = { viewModel.onEvent(MarketSellEvent.ValueCoinChanged(it)) },
                            textHint = stringResource(
                                id = R.string.sell_one_coin,
                                viewState.coinForSell,
                                viewState.symbolCoin
                            )
                        )
                    }
                }
                item {
                    Column {
                        Spacer(modifier = Modifier.height(18.dp))
                        Title(text = R.string.sell_one_coin_title, viewState.nameCurrency)
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
                                viewState.symbolCurrency,
                                viewState.valueCurrency
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
                            text = stringResource(R.string.sell_alert),
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
                text = R.string.detail_sell,
                color = MaterialTheme.colorScheme.secondary,
                onClick = { viewModel.onEvent(MarketSellEvent.SellButtonClicked) }
            )
        }
    }

}

@Composable
private fun ReactEffect(
    viewModel: MarketSellViewModel,
    moveReturn: () -> Unit
) {
    val viewEffect = viewModel.viewEffect
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                MarketSellViewEffect.MoveReturn -> moveReturn()
                is MarketSellViewEffect.ShowError -> showError(context, effect.message)
            }
        }
    }
}

@Composable
fun SellAllButton(
    text: Int,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier
            .height(36.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        shape = RoundedCornerShape(10.dp),
        onClick = onClick
    ) {
        androidx.compose.material3.Text(
            text = stringResource(text)
        )
    }
}
