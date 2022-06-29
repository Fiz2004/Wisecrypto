package com.fiz.wisecrypto.ui.screens.main.home.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.MainViewModel
import com.fiz.wisecrypto.ui.screens.main.components.coinList
import com.fiz.wisecrypto.ui.screens.main.home.components.BalanceInfo
import com.fiz.wisecrypto.ui.screens.main.home.components.TitleWatchlist
import com.fiz.wisecrypto.ui.screens.main.home.components.TitleYourActive
import com.fiz.wisecrypto.ui.screens.main.home.components.YourActive
import com.fiz.wisecrypto.ui.screens.main.home.main.components.UserInfo

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

    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                HomeViewEffect.MoveNotificationScreen -> {
                    moveNotificationScreen()
                }
                HomeViewEffect.MoveSignIn -> {

                }

            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp)
    ) {

        item {
            Spacer(
                modifier = Modifier
                    .windowInsetsTopHeight(WindowInsets.statusBars)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            UserInfo(
                icon = R.drawable.home_pic_avatar_test,
                fullName = viewState.fullName,
                onClickIconButton = { viewModel.onEvent(HomeEvent.NotificationClicked) }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            BalanceInfo()
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            TitleYourActive()
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            YourActive()
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            TitleWatchlist()
            Spacer(modifier = Modifier.height(8.dp))
        }

        coinList(viewState.coins)
    }
}


