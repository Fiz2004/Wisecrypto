package com.fiz.wisecrypto.ui.screens.main.home.main

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.components.BoxProgress
import com.fiz.wisecrypto.ui.screens.main.components.MainColumnWithBottomBar
import com.fiz.wisecrypto.ui.screens.main.home.main.components.PortfolioInfo
import com.fiz.wisecrypto.ui.screens.main.home.main.components.UserInfo
import com.fiz.wisecrypto.ui.screens.main.home.main.components.Watchlist
import com.fiz.wisecrypto.ui.screens.main.home.main.components.YourActive

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    moveNotificationScreen: () -> Unit,
    moveHomePortfolioScreen: () -> Unit,
    moveHomeDetailScreen: (String) -> Unit
) {
    val context = LocalContext.current

    val viewState = viewModel.viewState
    val viewEffect = viewModel.viewEffect

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.onEvent(HomeEvent.Started)
            } else if (event == Lifecycle.Event.ON_STOP) {
                viewModel.onEvent(HomeEvent.Stopped)
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
                HomeViewEffect.MoveNotificationScreen -> {
                    moveNotificationScreen()
                }
                HomeViewEffect.MoveHomePortfolioScreen -> {
                    moveHomePortfolioScreen()
                }
                is HomeViewEffect.MoveHomeDetailScreen -> {
                    moveHomeDetailScreen(effect.id)
                }
                is HomeViewEffect.ShowError -> {
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

    MainColumnWithBottomBar {
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
        YourActive(
            viewState.actives,
            { viewModel.onEvent(HomeEvent.YourActiveAllClicked) },
            { id -> viewModel.onEvent(HomeEvent.YourActiveClicked(id)) })
        Watchlist(coins = viewState.watchlist,
            { id -> viewModel.onEvent(HomeEvent.YourActiveClicked(id)) })
    }

    BoxProgress(viewState.isLoading)
}

