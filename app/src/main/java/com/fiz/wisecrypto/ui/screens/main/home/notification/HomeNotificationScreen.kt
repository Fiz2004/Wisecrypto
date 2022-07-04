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
import com.fiz.wisecrypto.ui.screens.main.components.Toolbar
import com.fiz.wisecrypto.ui.screens.main.home.notification.components.NotificationItem
import com.fiz.wisecrypto.ui.screens.main.home.notification.models.Notification
import com.fiz.wisecrypto.ui.screens.main.home.notification.models.StatusNotification
import com.fiz.wisecrypto.ui.screens.main.home.notification.models.TypeNotification
import org.threeten.bp.LocalDateTime

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

    val notifications = listOf(
        Notification(
            StatusNotification.Chart,
            TypeNotification.Info,
            stringResource(R.string.notification_title_portfolio_increase),
            stringResource(
                R.string.notification_text_portfolio_increase,
                "биткойн",
                "1,1%"
            ),
            LocalDateTime.of(2021, 11, 29, 13, 0)
        ),
        Notification(
            StatusNotification.Success,
            TypeNotification.Transaction(0.00001),
            stringResource(R.string.notification_title_transaction_success),
            stringResource(
                R.string.notification_text_transaction_success,
                "0.00001"
            ),
            LocalDateTime.of(2021, 11, 29, 13, 0)
        ),
        Notification(
            StatusNotification.Process,
            TypeNotification.Balance(10),
            stringResource(R.string.notification_title_balance_process),
            stringResource(
                R.string.notification_text_balance_process,
                "10"
            ),
            LocalDateTime.of(2021, 11, 29, 13, 0)
        ),
        Notification(
            StatusNotification.Success,
            TypeNotification.Balance(10),
            stringResource(R.string.notification_title_balance_success),
            stringResource(
                R.string.notification_text_balance_success,
                "10"
            ),
            LocalDateTime.of(2021, 11, 29, 13, 0)
        ),
        Notification(
            StatusNotification.Fail,
            TypeNotification.Balance(10),
            stringResource(R.string.notification_title_balance_fail),
            stringResource(
                R.string.notification_text_balance_fail,
                "10"
            ),
            LocalDateTime.of(2021, 11, 29, 13, 0)
        ),
    )

    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                HomeNotificationViewEffect.MoveReturn -> {
                    moveHomeMain()
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

        Spacer(
            modifier = Modifier
                .windowInsetsTopHeight(WindowInsets.statusBars)
        )

        Toolbar(
            title = stringResource(R.string.notification_notifications),
            onClickBackButton = { viewModel.onEvent(HomeNotificationEvent.BackButtonClicked) }
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {

            notifications.forEach { notification ->
                item {
                    NotificationItem(notification)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

        }
    }
}



