package com.fiz.wisecrypto.ui.screens.main.home.detail

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.components.BoxProgress
import com.fiz.wisecrypto.ui.screens.main.components.MainColumnWithoutBottomBar
import com.fiz.wisecrypto.ui.screens.main.components.NavigationBar
import com.fiz.wisecrypto.ui.screens.main.home.detail.components.Commands
import com.fiz.wisecrypto.ui.screens.main.home.detail.components.Overview
import com.fiz.wisecrypto.ui.screens.main.home.detail.components.Statistics
import com.fiz.wisecrypto.ui.screens.main.home.detail.components.YourActiveDetail

@Composable
fun HomeDetailScreen(
    viewModel: HomeDetailViewModel = viewModel(),
    id: String,
    moveHomeMainScreen: () -> Unit
) {

    val viewState = viewModel.viewState
    val viewEffect = viewModel.viewEffect

    val lifecycleOwner = LocalLifecycleOwner.current

    val context = LocalContext.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.onEvent(HomeDetailEvent.Started)
            } else if (event == Lifecycle.Event.ON_STOP) {
                viewModel.onEvent(HomeDetailEvent.Stopped)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(HomeDetailEvent.Create(id))
        viewEffect.collect { effect ->
            when (effect) {
                HomeDetailViewEffect.MoveReturn -> {
                    moveHomeMainScreen()
                }
                is HomeDetailViewEffect.ShowError -> {
                    val text = context.getString(
                        if (effect.message == null)
                            R.string.error_network_default
                        else
                            R.string.error_network, effect.message
                    )
                    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Box {
        MainColumnWithoutBottomBar(
            textToolbar = viewState.name,
            isShowAddWatchList = true,
            isValueAddWatchList = viewState.isWatchList,
            onClickBackButton = { viewModel.onEvent(HomeDetailEvent.BackButtonClicked) },
            onClickAddWatchList = { viewModel.onEvent(HomeDetailEvent.AddWatchListClicked) }
        ) {
            LazyColumn {
                item {
                    Statistics(
                        priceForOne = viewState.priceOne,
                        symbol = viewState.abbreviated,
                        increased = viewState.pricePortfolioIncreased,
                        changePercentagePricePortfolio = viewState.changePercentagePricePortfolio,
                        indexPeriod = viewState.indexPeriodFilterChip.ordinal,
                        valueCurrentPriceHistory = viewState.currentPriceHistoryValue,
                        labels = viewState.currentPriceHistoryLabel,
                        onClickPeriod = { index ->
                            viewModel.onEvent(
                                HomeDetailEvent.PeriodFilterChipClicked(
                                    index
                                )
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    Overview(
                        allTimeHigh = viewState.allTimeHigh,
                        allTimeLow = viewState.allTimeLow,
                        marketCap = viewState.marketCap,
                        totalVolume = viewState.totalVolume,
                        totalSupply = viewState.totalSupply,
                        maxSupply = viewState.maxSupply,
                        marketCapChange = viewState.marketCapChange24h,
                        totalVolumeChange = viewState.totalVolumeChange24h,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                if (viewState.yourActive != null)
                    item {
                        YourActiveDetail(viewState.yourActive)
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                item {
                    // Если поменяется высота Commands, изменить согласно новой высоте
                    Spacer(modifier = Modifier.height(82.dp))

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            Commands()
            NavigationBar()
        }
    }

    BoxProgress(viewState.isLoading)
}



