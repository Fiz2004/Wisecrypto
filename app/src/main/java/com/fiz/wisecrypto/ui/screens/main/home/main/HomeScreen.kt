package com.fiz.wisecrypto.ui.screens.main.home.main

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.components.FullScreenLoading
import com.fiz.wisecrypto.ui.screens.main.components.LoadingContent
import com.fiz.wisecrypto.ui.screens.main.components.MainColumn
import com.fiz.wisecrypto.ui.screens.main.home.main.components.PortfolioInfo
import com.fiz.wisecrypto.ui.screens.main.home.main.components.UserInfo
import com.fiz.wisecrypto.ui.screens.main.home.main.components.WatchList
import com.fiz.wisecrypto.ui.screens.main.home.main.components.YourActive
import com.fiz.wisecrypto.ui.util.LifeCycleEffect
import com.fiz.wisecrypto.ui.util.showError

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    moveNotificationScreen: () -> Unit,
    moveHomePortfolioScreen: () -> Unit,
    moveMarketDetailScreen: (String) -> Unit
) {
    LifeCycleEffect(viewModel)
    ReactEffect(viewModel, moveNotificationScreen, moveHomePortfolioScreen, moveMarketDetailScreen)

    val viewState = viewModel.viewState
    LoadingContent(
        empty = viewState.watchlist == null,
        emptyContent = { FullScreenLoading() },
        loading = viewState.isLoading,
        onRefresh = { viewModel.onEvent(HomeEvent.OnRefresh) }
    ) {
        MainColumn {
            UserInfo(
                icon = R.drawable.pic_avatar_test,
                fullName = viewState.fullName,
                onClickIconButton = { viewModel.onEvent(HomeEvent.NotificationClicked) }
            )
            PortfolioInfo(
                viewState.pricePortfolio,
                viewState.pricePortfolioIncreased,
                viewState.changePercentageBalance,
                viewState.balance
            )
            LazyColumn {
                item {
                    YourActive(
                        viewState.actives ?: emptyList(),
                        { viewModel.onEvent(HomeEvent.YourActiveAllClicked) },
                        { id -> viewModel.onEvent(HomeEvent.YourActiveClicked(id)) })
                }
                item {
                    WatchList(viewState.watchlist ?: emptyList()) { id ->
                        viewModel.onEvent(
                            HomeEvent.YourActiveClicked(id)
                        )
                    }
                }

            }
        }
    }
}

@Composable
private fun ReactEffect(
    viewModel: HomeViewModel,
    moveNotificationScreen: () -> Unit,
    moveHomePortfolioScreen: () -> Unit,
    moveHomeDetailScreen: (String) -> Unit
) {
    val context = LocalContext.current
    val viewEffect = viewModel.viewEffect
    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                HomeViewEffect.MoveNotificationScreen -> moveNotificationScreen()
                HomeViewEffect.MoveHomePortfolioScreen -> moveHomePortfolioScreen()
                is HomeViewEffect.MoveHomeDetailScreen -> moveHomeDetailScreen(effect.id)
                is HomeViewEffect.ShowError -> showError(context, effect.message)
            }
        }
    }
}

