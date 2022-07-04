package com.fiz.wisecrypto.ui.screens.main.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.MainViewModel
import com.fiz.wisecrypto.ui.screens.main.components.TopSpacer
import com.fiz.wisecrypto.ui.screens.main.profile.components.BalanceInfo
import com.fiz.wisecrypto.ui.screens.main.profile.components.ProfileMenuItem
import com.fiz.wisecrypto.ui.screens.main.profile.components.UserInfoLarge
import com.fiz.wisecrypto.ui.theme.*

@Composable
fun ProfileScreen(
    MainViewModel: MainViewModel = viewModel(),
    viewModel: ProfileViewModel = viewModel(),
    movePullScreen: () -> Unit,
    moveAddScreen: () -> Unit,
    moveListTransactionsScreen: () -> Unit,
    movePrivacyScreen: () -> Unit,
    movePaymentScreen: () -> Unit,
    moveNotificationsScreen: () -> Unit,
    moveSignInScreen: () -> Unit
) {

    val context = LocalContext.current

    val viewState = viewModel.viewState
    val mainViewState = MainViewModel.viewState
    val viewEffect = viewModel.viewEffect


    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                ProfileViewEffect.MoveAddScreen -> {
                    moveAddScreen()
                }
                ProfileViewEffect.MoveListTransactionsScreen -> {
                    moveListTransactionsScreen()
                }
                ProfileViewEffect.MoveNotificationsScreen -> {
                    moveNotificationsScreen()
                }
                ProfileViewEffect.MovePaymentScreen -> {
                    movePaymentScreen()
                }
                ProfileViewEffect.MovePrivacyScreen -> {
                    movePrivacyScreen()
                }
                ProfileViewEffect.MovePullScreen -> {
                    movePullScreen()
                }
                ProfileViewEffect.MoveSignInScreen -> {
                    moveSignInScreen()
                }
                is ProfileViewEffect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_LONG).show()
                }
                ProfileViewEffect.ShowChangeAvatarScreen -> {

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

        UserInfoLarge(
            fullName = viewState.fullName,
            onClickChangeAvatar = { viewModel.onEvent(ProfileEvent.ChangeAvatarClicked) }
        )

        BalanceInfo(
            currentBalance = viewState.balanceCurrentCurrency,
            currentBalanceUsd = viewState.balanceUsd,
            onClickPull = { viewModel.onEvent(ProfileEvent.PullClicked) },
            onClickAdd = { viewModel.onEvent(ProfileEvent.AddClicked) },
        )

        ProfileMenuItem(
            icon = R.drawable.profile_ic_list_transactions,
            color = LightGreen2,
            stringResource(R.string.profile_list_transactions_title),
            stringResource(R.string.profile_list_transactions_description),
            onClick = { viewModel.onEvent(ProfileEvent.ListTransactionsClicked) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.profile_settings),
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {

            item {
                ProfileMenuItem(
                    icon = R.drawable.profile_ic_privacy,
                    color = LightBlue2,
                    stringResource(R.string.profile_privacy_title),
                    stringResource(R.string.profile_privacy_description),
                    onClick = { viewModel.onEvent(ProfileEvent.PrivacyClicked) }
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                ProfileMenuItem(
                    icon = R.drawable.profile_ic_payment,
                    color = LightPurple2,
                    stringResource(R.string.profile_payment_title),
                    stringResource(R.string.profile_payment_description),
                    onClick = { viewModel.onEvent(ProfileEvent.PaymentClicked) }
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                ProfileMenuItem(
                    icon = R.drawable.profile_ic_notification,
                    color = LightYellow2,
                    stringResource(R.string.profile_notifications_title),
                    stringResource(R.string.profile_notifications_description),
                    onClick = { viewModel.onEvent(ProfileEvent.NotificationsClicked) }
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                ProfileMenuItem(
                    icon = R.drawable.profile_ic_exit,
                    color = LightRed2,
                    stringResource(R.string.profile_exit_title),
                    stringResource(R.string.profile_exit_description),
                    onClick = { viewModel.onEvent(ProfileEvent.ProfileExitClicked) }
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }

    }
}


