package com.fiz.wisecrypto.ui.screens.main.market

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.components.BoxProgress
import com.fiz.wisecrypto.ui.screens.main.components.CoinColumn
import com.fiz.wisecrypto.ui.screens.main.components.MainColumnWithBottomBar
import com.fiz.wisecrypto.ui.screens.main.market.components.FilterRow
import com.fiz.wisecrypto.ui.screens.main.market.components.FindTextField

@Composable
fun MarketScreen(
    viewModel: MarketViewModel = viewModel()
) {

    val context = LocalContext.current

    val viewState = viewModel.viewState
    val viewEffect = viewModel.viewEffect

    val textHint = stringResource(R.string.market_search_hint)

    val lifecycleOwner = LocalLifecycleOwner.current

    val marketFilters = listOf(
        stringResource(R.string.market_favorities),
        stringResource(R.string.market_fiat),
        stringResource(R.string.market_etf),
        stringResource(R.string.market_bnb),
        stringResource(R.string.market_btc),
        stringResource(R.string.market_alts)
    )

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.onEvent(MarketEvent.Started)
            } else if (event == Lifecycle.Event.ON_STOP) {
                viewModel.onEvent(MarketEvent.Stopped)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                is MarketViewEffect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    MainColumnWithBottomBar {
        FindTextField(
            text = viewState.searchText,
            onValueChange = {
                viewModel.onEvent(MarketEvent.SearchTextChanged(it))
            },
            textHint = textHint
        )

        FilterRow(
            texts = marketFilters,
            indexSelected = viewState.selectedChipNumber,
            onClick = { number -> viewModel.onEvent(MarketEvent.MarketChipClicked(number)) }
        )

        CoinColumn(viewState.coins)
    }

    BoxProgress(viewState.isLoading)
}

