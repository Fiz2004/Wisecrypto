package com.fiz.wisecrypto.ui.screens.main.home.notification

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.components.MainColumn
import com.fiz.wisecrypto.ui.screens.main.home.notification.components.NotificationsList

@Composable
fun HomeNotificationScreen(
    viewModel: HomeNotificationViewModel = viewModel(),
    moveReturn: () -> Unit
) {
    ReactEffect(viewModel, moveReturn)

    val viewState = viewModel.viewState
    MainColumn(
        textToolbar = stringResource(R.string.notification_notifications),
        onClickBackButton = { viewModel.onEvent(HomeNotificationEvent.BackButtonClicked) }
    ) {
        NotificationsList(viewState.notifications)
    }
}

@Composable
private fun ReactEffect(
    viewModel: HomeNotificationViewModel,
    moveReturn: () -> Unit
) {
    val viewEffect = viewModel.viewEffect
    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                HomeNotificationViewEffect.MoveReturn -> {
                    moveReturn()
                }
            }
        }
    }
}

