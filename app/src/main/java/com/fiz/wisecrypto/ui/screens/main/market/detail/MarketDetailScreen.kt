package com.fiz.wisecrypto.ui.screens.main.market.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.ui.screens.main.components.FullScreenLoading
import com.fiz.wisecrypto.ui.screens.main.components.LoadingContent
import com.fiz.wisecrypto.ui.screens.main.components.MainColumn
import com.fiz.wisecrypto.ui.screens.main.market.detail.components.Commands
import com.fiz.wisecrypto.ui.screens.main.market.detail.components.Overview
import com.fiz.wisecrypto.ui.screens.main.market.detail.components.Statistics
import com.fiz.wisecrypto.ui.screens.main.market.detail.components.YourActiveDetail
import com.fiz.wisecrypto.ui.util.LifeCycleEffect
import com.fiz.wisecrypto.ui.util.showError

// TODO Сдлеать возврат при неудачной загрузке предыдущего выбора

@Composable
fun MarketDetailScreen(
    viewModel: MarketDetailViewModel = viewModel(),
    moveReturnScreen: () -> Unit,
    moveMarketSellScreen: () -> Unit,
    moveMarketBuyScreen: () -> Unit
) {
    LifeCycleEffect(viewModel)
    ReactEffect(viewModel, moveReturnScreen, moveMarketSellScreen, moveMarketBuyScreen)

    val viewState = viewModel.viewState
    LoadingContent(
        empty = viewState.priceOne == null,
        emptyContent = { FullScreenLoading() },
        loading = viewState.isLoading,
        onRefresh = { viewModel.onEvent(MarketDetailEvent.OnRefresh) }
    ) {
        Box {
            MainColumn(
                textToolbar = viewState.name,
                isShowAddWatchList = true,
                isValueAddWatchList = viewState.isWatchList,
                onClickBackButton = { viewModel.onEvent(MarketDetailEvent.BackButtonClicked) },
                onClickAddWatchList = { viewModel.onEvent(MarketDetailEvent.AddWatchListClicked) }
            ) {
                LazyColumn {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Statistics(
                            viewModel = viewModel,
                            priceForOne = viewState.priceOne ?: "",
                            symbol = viewState.abbreviated,
                            increased = viewState.pricePortfolioIncreased,
                            changePercentagePricePortfolio = viewState.changePercentagePricePortfolio,
                            indexPeriod = viewState.indexPeriodFilterChip.ordinal,
                            valueCurrentPriceHistory = viewState.currentPriceHistoryValue,
                            labels = viewState.currentPriceHistoryLabel,
                            onClickPeriod = { index ->
                                viewModel.onEvent(
                                    MarketDetailEvent.PeriodFilterChipClicked(
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
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .imePadding()
            ) {
                Commands(
                    isButtonSellVisible = viewState.yourActive != null,
                    onClickButtonSell = { viewModel.onEvent(MarketDetailEvent.SellButtonClicked) },
                    onClickButtonBuy = { viewModel.onEvent(MarketDetailEvent.BuyButtonClicked) }
                )
            }
        }
    }


}

@Composable
private fun ReactEffect(
    viewModel: MarketDetailViewModel = viewModel(),
    moveHomeMainScreen: () -> Unit,
    moveMarketSellScreen: () -> Unit,
    moveMarketBuyScreen: () -> Unit
) {
    val viewEffect = viewModel.viewEffect
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                MarketDetailViewEffect.MoveReturn -> moveHomeMainScreen()
                MarketDetailViewEffect.MoveMarketSellScreen -> moveMarketSellScreen()
                MarketDetailViewEffect.MoveMarketBuyScreen -> moveMarketBuyScreen()
                is MarketDetailViewEffect.ShowError -> showError(context, effect.message)
            }
        }
    }
}



