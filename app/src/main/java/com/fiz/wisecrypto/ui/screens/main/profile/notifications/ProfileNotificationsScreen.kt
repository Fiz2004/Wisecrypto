package com.fiz.wisecrypto.ui.screens.main.profile.notifications

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.components.PrimaryButton
import com.fiz.wisecrypto.ui.screens.main.components.Toolbar

@Composable
fun ProfileNotificationsScreen(
    viewModel: ProfileNotificationsViewModel = viewModel(),
    moveReturn: () -> Unit,
) {

    val context = LocalContext.current

    val viewState = viewModel.viewState
    val viewEffect = viewModel.viewEffect

    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                ProfileNotificationsViewEffect.MoveReturn -> {
                    moveReturn()
                }
                is ProfileNotificationsViewEffect.ShowToast -> {
                    val text = context.getString(effect.message)
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
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
            title = stringResource(R.string.notifications_title),
            onClickBackButton = { viewModel.onEvent(ProfileNotificationsEvent.BackButtonClicked) }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .padding(horizontal = 16.dp)
        ) {

            Text(
                text = stringResource(R.string.notifications_types_notifications),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 0.3.dp)
            Spacer(modifier = Modifier.height(16.dp))

            RowNotification(
                text = stringResource(R.string.notifications_portfolio),
                valueSwitch = viewState.portfolioSwitchValue,
                onCheckedChange = { viewModel.onEvent(ProfileNotificationsEvent.PortfolioSwitchClicked) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            RowNotification(
                text = stringResource(R.string.notifications_popular),
                valueSwitch = viewState.popularSwitchValue,
                onCheckedChange = { viewModel.onEvent(ProfileNotificationsEvent.PopularSwitchClicked) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            RowNotification(
                text = stringResource(R.string.notifications_watchlist),
                valueSwitch = viewState.watchlistSwitchValue,
                onCheckedChange = { viewModel.onEvent(ProfileNotificationsEvent.WatchlistSwitchClicked) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            RowNotification(
                text = stringResource(R.string.notifications_promotions),
                valueSwitch = viewState.promotionsSwitchValue,
                onCheckedChange = { viewModel.onEvent(ProfileNotificationsEvent.PromotionsSwitchClicked) }
            )

        }

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            text = R.string.notifications_save,
            onClick = { viewModel.onEvent(ProfileNotificationsEvent.SaveButtonClicked) })
    }
}

@Composable
private fun RowNotification(
    text: String,
    valueSwitch: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(checked = valueSwitch, onCheckedChange = onCheckedChange)
    }
}


