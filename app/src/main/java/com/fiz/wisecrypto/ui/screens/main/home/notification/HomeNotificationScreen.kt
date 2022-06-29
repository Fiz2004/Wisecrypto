package com.fiz.wisecrypto.ui.screens.main.home.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.MainViewModel
import com.fiz.wisecrypto.ui.screens.main.home.notification.components.NotificationItem
import com.fiz.wisecrypto.ui.screens.main.home.notification.components.Toolbar
import com.fiz.wisecrypto.ui.theme.LightGreen2
import com.fiz.wisecrypto.ui.theme.LightRed2
import com.fiz.wisecrypto.ui.theme.LightYellow2

@Composable
fun HomeNotificationScreen(
    MainViewModel: MainViewModel = viewModel(),
    viewModel: HomeNotificationViewModel = viewModel(),
    moveHomeMain: () -> Unit
) {
    val context = LocalContext.current

    val viewState = viewModel.viewState
    val mainViewState = MainViewModel.viewState
    val viewEffect = viewModel.viewEffect

    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                HomeNotificationViewEffect.MoveReturn -> {
                    moveHomeMain()
                }
                HomeNotificationViewEffect.MoveSignIn -> {

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

            Spacer(modifier = Modifier.height(4.dp))

        }

        item {
            Toolbar(
                onClickBackButton = { viewModel.onEvent(HomeNotificationEvent.BackButtonClicked) }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(5) { index ->
            when (index) {
                0 -> {
                    NotificationItem(
                        icon = R.drawable.notification_ic_chart,
                        color = LightGreen2,
                        titleNotification = stringResource(R.string.notification_title_portfolio_increase),
                        textNotification = stringResource(
                            R.string.notification_text_portfolio_increase,
                            "биткойн",
                            "1,1%"
                        ),
                        data = stringResource(R.string.notification_date),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                1 -> {
                    NotificationItem(
                        icon = R.drawable.notification_ic_ok,
                        color = LightGreen2,
                        titleNotification = stringResource(R.string.notification_title_transaction_success),
                        textNotification = stringResource(
                            R.string.notification_text_transaction_success,
                            "0.00001"
                        ),
                        data = stringResource(R.string.notification_date),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                2 -> {
                    NotificationItem(
                        icon = R.drawable.notification_ic_time,
                        color = LightYellow2,
                        titleNotification = stringResource(R.string.notification_title_balance_process),
                        textNotification = stringResource(
                            R.string.notification_text_balance_process,
                            "10"
                        ),
                        data = stringResource(R.string.notification_date),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                3 -> {
                    NotificationItem(
                        icon = R.drawable.notification_ic_ok,
                        color = LightGreen2,
                        titleNotification = stringResource(R.string.notification_title_balance_success),
                        textNotification = stringResource(
                            R.string.notification_text_balance_success,
                            "10"
                        ),
                        data = stringResource(R.string.notification_date),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                4 -> {
                    NotificationItem(
                        icon = R.drawable.notification_ic_cancel,
                        color = LightRed2,
                        titleNotification = stringResource(R.string.notification_title_balance_fail),
                        textNotification = stringResource(
                            R.string.notification_text_balance_fail,
                            "10"
                        ),
                        data = stringResource(R.string.notification_date),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}



