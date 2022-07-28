package com.fiz.wisecrypto.ui.screens.main.navigate.graphs

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fiz.wisecrypto.ui.screens.main.navigate.names.NamesMainScreen
import com.fiz.wisecrypto.ui.screens.main.navigate.names.NamesMarketScreen
import com.fiz.wisecrypto.ui.screens.main.navigate.names.NamesProfileScreen
import com.fiz.wisecrypto.ui.screens.main.profile.list_transactions.ProfileListTransactionsScreen
import com.fiz.wisecrypto.ui.screens.main.profile.list_transactions.ProfileListTransactionsViewModel
import com.fiz.wisecrypto.ui.screens.main.profile.main.ProfileScreen
import com.fiz.wisecrypto.ui.screens.main.profile.main.ProfileViewModel
import com.fiz.wisecrypto.ui.screens.main.profile.notifications.ProfileNotificationsScreen
import com.fiz.wisecrypto.ui.screens.main.profile.notifications.ProfileNotificationsViewModel
import com.fiz.wisecrypto.ui.screens.main.profile.payment.ProfilePaymentScreen
import com.fiz.wisecrypto.ui.screens.main.profile.payment.ProfilePaymentViewModel
import com.fiz.wisecrypto.ui.screens.main.profile.privacy.ProfilePrivacyScreen
import com.fiz.wisecrypto.ui.screens.main.profile.privacy.ProfilePrivacyViewModel

fun NavGraphBuilder.profileGraph(navController: NavHostController, moveReturn: () -> Unit) {
    navigation(
        startDestination = NamesProfileScreen.ProfileMain.name,
        route = NamesMainScreen.Profile.name
    ) {
        composable(NamesProfileScreen.ProfileMain.name) {
            val viewModel = hiltViewModel<ProfileViewModel>()
            ProfileScreen(
                viewModel,
                moveMarketCashBalanceScreen = { navController.navigate(NamesMarketScreen.CashBalance.name) },
                moveMarketAddBalanceScreen = { navController.navigate(NamesMarketScreen.AddBalance.name) },
                moveListTransactionsScreen = { navController.navigate(NamesProfileScreen.ListTransactions.name) },
                movePrivacyScreen = { navController.navigate(NamesProfileScreen.Privacy.name) },
                movePaymentScreen = { navController.navigate(NamesProfileScreen.Payment.name) },
                moveNotificationsScreen = { navController.navigate(NamesProfileScreen.Notifications.name) },
                moveSignInScreen = {
                    moveReturn()
                }
            )
        }

        composable(NamesProfileScreen.ListTransactions.name) {
            val viewModel = hiltViewModel<ProfileListTransactionsViewModel>()

            ProfileListTransactionsScreen(
                viewModel,
                moveReturn = { navController.popBackStack() }
            )
        }

        composable(NamesProfileScreen.Privacy.name) {
            val viewModel = hiltViewModel<ProfilePrivacyViewModel>()

            ProfilePrivacyScreen(
                viewModel,
                moveReturn = { navController.popBackStack() }
            )
        }

        composable(NamesProfileScreen.Payment.name) {
            val viewModel = hiltViewModel<ProfilePaymentViewModel>()

            ProfilePaymentScreen(
                viewModel,
                moveReturn = { navController.popBackStack() }
            )
        }

        composable(NamesProfileScreen.Notifications.name) {
            val viewModel = hiltViewModel<ProfileNotificationsViewModel>()

            ProfileNotificationsScreen(
                viewModel,
                moveReturn = { navController.popBackStack() }
            )
        }
    }
}