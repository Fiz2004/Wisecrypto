package com.fiz.wisecrypto.ui.screens.main.home.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.components.Progress
import com.fiz.wisecrypto.ui.screens.main.MainViewModel
import com.fiz.wisecrypto.ui.screens.main.components.CoinItem
import com.fiz.wisecrypto.ui.screens.main.components.TopSpacer
import com.fiz.wisecrypto.ui.screens.main.home.components.TitleWatchlist
import com.fiz.wisecrypto.ui.screens.main.home.main.components.PortfolioInfo
import com.fiz.wisecrypto.ui.screens.main.home.main.components.TitleYourActive
import com.fiz.wisecrypto.ui.screens.main.home.main.components.UserInfo
import com.fiz.wisecrypto.ui.screens.main.home.main.components.YourActive

@Composable
fun Lifecycle.observeAsState(): State<Lifecycle.Event> {
    val state = remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(this) {
        val observer = LifecycleEventObserver { _, event ->
            state.value = event
        }
        this@observeAsState.addObserver(observer)
        onDispose {
            this@observeAsState.removeObserver(observer)
        }
    }
    return state
}

@Composable
fun HomeScreen(
    MainViewModel: MainViewModel = viewModel(),
    viewModel: HomeViewModel = viewModel(),
    moveNotificationScreen: () -> Unit
) {
    val context = LocalContext.current

    val viewState = viewModel.viewState
    val mainViewState = MainViewModel.viewState
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
                HomeViewEffect.MoveSignIn -> {

                }
                is HomeViewEffect.ShowError -> {
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

        UserInfo(
            icon = R.drawable.pic_avatar_test,
            fullName = viewState.fullName,
            onClickIconButton = { viewModel.onEvent(HomeEvent.NotificationClicked) }
        )

        PortfolioInfo(viewState.balance, viewState.changePercentageBalance)

        TitleYourActive()

        YourActive(viewState.portfolio)

        TitleWatchlist()

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


