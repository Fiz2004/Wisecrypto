package com.fiz.wisecrypto.ui.screens.main.profile.main

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.components.MainColumn
import com.fiz.wisecrypto.ui.screens.main.profile.main.components.BalanceInfo
import com.fiz.wisecrypto.ui.screens.main.profile.main.components.ExitDialog
import com.fiz.wisecrypto.ui.screens.main.profile.main.components.ProfileMenuItem
import com.fiz.wisecrypto.ui.screens.main.profile.main.components.UserInfoLarge
import com.fiz.wisecrypto.ui.theme.*
import com.fiz.wisecrypto.util.showError

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(),
    moveMarketCashBalanceScreen: () -> Unit,
    moveMarketAddBalanceScreen: () -> Unit,
    moveListTransactionsScreen: () -> Unit,
    movePrivacyScreen: () -> Unit,
    movePaymentScreen: () -> Unit,
    moveNotificationsScreen: () -> Unit,
    moveSignInScreen: () -> Unit
) {

    val openDialog = rememberSaveable { mutableStateOf(false) }

    ReactEffect(
        viewModel,
        moveMarketCashBalanceScreen,
        moveMarketAddBalanceScreen,
        moveListTransactionsScreen,
        movePrivacyScreen,
        movePaymentScreen,
        moveNotificationsScreen,
        moveSignInScreen
    ) { openDialog.value = true }

    val viewState = viewModel.viewState
    MainColumn {
        LazyColumn {

            item {
                UserInfoLarge(
                    fullName = viewState.fullName,
                    onClickChangeAvatar = { viewModel.onEvent(ProfileEvent.ChangeAvatarClicked) }
                )
            }

            item {
                BalanceInfo(
                    currentBalance = viewState.balanceCurrentCurrency,
                    currentBalanceUsd = viewState.balanceUsd,
                    onClickPull = { viewModel.onEvent(ProfileEvent.PullClicked) },
                    onClickAdd = { viewModel.onEvent(ProfileEvent.AddClicked) },
                )
            }

            item {
                ProfileMenuItem(
                    icon = R.drawable.profile_ic_list_transactions,
                    color = LightGreen2,
                    stringResource(R.string.profile_list_transactions_title),
                    stringResource(R.string.profile_list_transactions_description),
                    onClick = { viewModel.onEvent(ProfileEvent.ListTransactionsClicked) }
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Text(
                    text = stringResource(R.string.profile_settings),
                    style = MaterialTheme.typography.displayMedium
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

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

    ExitDialog(
        openDialog = openDialog.value,
        onClickConfirm = {
            openDialog.value = false
            viewModel.onEvent(ProfileEvent.ConfirmExitClicked)
        },
        onClickDismiss = {
            openDialog.value = false
            viewModel.onEvent(ProfileEvent.CancelExitClicked)
        }
    )
}

@Composable
private fun ReactEffect(
    viewModel: ProfileViewModel = viewModel(),
    moveMarketCashBalanceScreen: () -> Unit,
    moveMarketAddBalanceScreen: () -> Unit,
    moveListTransactionsScreen: () -> Unit,
    movePrivacyScreen: () -> Unit,
    movePaymentScreen: () -> Unit,
    moveNotificationsScreen: () -> Unit,
    moveSignInScreen: () -> Unit,
    showAlertDialogConfirmExit: () -> Unit
) {
    val context = LocalContext.current
    val viewEffect = viewModel.viewEffect

    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                ProfileViewEffect.MoveAddScreen -> moveMarketAddBalanceScreen()
                ProfileViewEffect.MoveListTransactionsScreen -> moveListTransactionsScreen()
                ProfileViewEffect.MoveNotificationsScreen -> moveNotificationsScreen()
                ProfileViewEffect.MovePaymentScreen -> movePaymentScreen()
                ProfileViewEffect.MovePrivacyScreen -> movePrivacyScreen()
                ProfileViewEffect.MovePullScreen -> moveMarketCashBalanceScreen()
                ProfileViewEffect.MoveSignInScreen -> moveSignInScreen()
                is ProfileViewEffect.ShowError -> showError(context, effect.message)
                ProfileViewEffect.ShowChangeAvatarScreen -> {}
                ProfileViewEffect.ShowAlertDialogConfirmExit -> showAlertDialogConfirmExit()
            }
        }
    }
}


