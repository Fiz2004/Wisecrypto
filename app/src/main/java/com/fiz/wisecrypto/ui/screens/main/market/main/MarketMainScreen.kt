package com.fiz.wisecrypto.ui.screens.main.market.main

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.components.CoinItem
import com.fiz.wisecrypto.ui.screens.main.components.FullScreenLoading
import com.fiz.wisecrypto.ui.screens.main.components.LoadingContent
import com.fiz.wisecrypto.ui.screens.main.components.MainColumn
import com.fiz.wisecrypto.ui.screens.main.market.main.components.FilterRow
import com.fiz.wisecrypto.ui.screens.main.market.main.components.FindTextField
import com.fiz.wisecrypto.ui.util.LifeCycleEffect
import com.fiz.wisecrypto.util.showError

@Composable
fun MarketMainScreen(
    viewModel: MarketViewModel = viewModel(),
    state: LazyListState = rememberLazyListState(),
    moveMarketDetailScreen: (String) -> Unit
) {
    LifeCycleEffect(viewModel)
    ReactEffect(viewModel, moveMarketDetailScreen)

    val viewState = viewModel.viewState
    LoadingContent(
        empty = viewState.coins == null,
        emptyContent = { FullScreenLoading() },
        loading = viewState.isLoading,
        onRefresh = { viewModel.onEvent(MarketEvent.OnRefresh) }
    ) {
        MainColumn {
            FindTextField(
                text = viewState.searchText,
                onValueChange = {
                    viewModel.onEvent(MarketEvent.SearchTextChanged(it))
                },
                textHint = stringResource(R.string.market_search_hint)
            )
            FilterRow(
                texts = stringArrayResource(id = R.array.market_filters).toList(),
                indexSelected = viewState.selectedChipNumber,
                onClick = { number -> viewModel.onEvent(MarketEvent.MarketChipClicked(number)) }
            )
            LazyColumn(state = state) {
                items(
                    items = viewState.coins ?: listOf(),
                    key = { coin -> coin.id }
                ) { coin ->
                    CoinItem(coin) {
                        viewModel.onEvent(
                            MarketEvent.YourActiveClicked(it)
                        )
                    }
                }
            }
        }
    }

}

@Composable
private fun ReactEffect(
    viewModel: MarketViewModel,
    moveHomeDetailScreen: (String) -> Unit
) {
    val viewEffect = viewModel.viewEffect
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                is MarketViewEffect.ShowError -> showError(context, effect.message)
                is MarketViewEffect.MoveHomeDetailScreen -> moveHomeDetailScreen(effect.id)
            }
        }
    }
}

