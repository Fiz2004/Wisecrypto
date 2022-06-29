package com.fiz.wisecrypto.ui.screens.main.market

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.MainViewModel
import com.fiz.wisecrypto.ui.screens.main.components.coinList
import com.fiz.wisecrypto.ui.screens.main.market.components.filterRow
import com.fiz.wisecrypto.ui.screens.main.market.components.findTextField
import com.fiz.wisecrypto.ui.screens.main.market.components.topSpacer

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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp)
    ) {
        topSpacer()
        findTextField(
            text = viewState.searchText,
            onValueChange = {
                viewModel.onEvent(MarketEvent.SearchTextChanged(it))
            },
            textHint = textHint
        )
        filterRow(viewState.selectedChipNumber, viewModel)
        coinList(viewState.coins)
    }

}

