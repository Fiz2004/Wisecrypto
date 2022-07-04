package com.fiz.wisecrypto.ui.screens.main.market

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.components.Progress
import com.fiz.wisecrypto.ui.screens.main.MainViewModel
import com.fiz.wisecrypto.ui.screens.main.components.CoinItem
import com.fiz.wisecrypto.ui.screens.main.components.TopSpacer
import com.fiz.wisecrypto.ui.screens.main.market.components.FilterRow
import com.fiz.wisecrypto.ui.screens.main.market.components.FindTextField

@Composable
fun MarketScreen(
    MainViewModel: MainViewModel = viewModel(),
    viewModel: MarketViewModel = viewModel()
) {

    val context = LocalContext.current

    val viewState = viewModel.viewState
    val mainViewState = MainViewModel.viewState
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp)
    ) {

        TopSpacer()

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

        LazyColumn {
            viewState.coins.forEach {
                item {
                    CoinItem(it)
                }
            }
        }
    }

    if (viewState.isLoading)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Progress()
        }
}

