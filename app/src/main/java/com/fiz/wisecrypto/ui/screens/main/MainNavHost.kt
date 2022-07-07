package com.fiz.wisecrypto.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fiz.wisecrypto.ui.screens.main.home.main.HomeScreen
import com.fiz.wisecrypto.ui.screens.main.home.main.HomeViewModel
import com.fiz.wisecrypto.ui.screens.main.home.notification.HomeNotificationScreen
import com.fiz.wisecrypto.ui.screens.main.home.notification.HomeNotificationViewModel
import com.fiz.wisecrypto.ui.screens.main.market.MarketScreen
import com.fiz.wisecrypto.ui.screens.main.market.MarketViewModel
import com.fiz.wisecrypto.ui.screens.main.profile.list_transactions.ProfileListTransactionsScreen
import com.fiz.wisecrypto.ui.screens.main.profile.list_transactions.ProfileListTransactionsViewModel
import com.fiz.wisecrypto.ui.screens.main.profile.main.ProfileScreen
import com.fiz.wisecrypto.ui.screens.main.profile.main.ProfileViewModel
import com.fiz.wisecrypto.ui.screens.main.profile.payment.ProfilePaymentScreen
import com.fiz.wisecrypto.ui.screens.main.profile.payment.ProfilePaymentViewModel
import com.fiz.wisecrypto.ui.screens.main.profile.privacy.ProfilePrivacyScreen
import com.fiz.wisecrypto.ui.screens.main.profile.privacy.ProfilePrivacyViewModel

@Composable
fun MainNavHost(
    navController: NavHostController,
    moveReturn: () -> Unit,
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = NamesMainScreen.Home.name,
        modifier = modifier
    ) {
        composable(NamesMainScreen.Home.name) {
            val viewModel = hiltViewModel<HomeViewModel>()

            HomeScreen(
                mainViewModel,
                viewModel,
                moveNotificationScreen = { navController.navigate(NamesHomeScreen.Notification.name) }
            )
        }

        composable(NamesHomeScreen.Notification.name) {
            val viewModel = hiltViewModel<HomeNotificationViewModel>()

            HomeNotificationScreen(
                mainViewModel,
                viewModel,
                moveHomeMain = { navController.popBackStack() }
            )
        }

        composable(NamesMainScreen.Market.name) {
            val viewModel = hiltViewModel<MarketViewModel>()

            MarketScreen(
                mainViewModel,
                viewModel,
            )
        }

        composable(NamesMainScreen.Profile.name) {
            val viewModel = hiltViewModel<ProfileViewModel>()

            ProfileScreen(
                mainViewModel,
                viewModel,
                movePullScreen = {},
                moveAddScreen = {},
                moveListTransactionsScreen = { navController.navigate(NamesProfileScreen.ListTransactions.name) },
                movePrivacyScreen = { navController.navigate(NamesProfileScreen.Privacy.name) },
                movePaymentScreen = { navController.navigate(NamesProfileScreen.Payment.name) },
                moveNotificationsScreen = {},
                moveSignInScreen = {
                    moveReturn()
                }
            )
        }

        composable(NamesProfileScreen.ListTransactions.name) {
            val viewModel = hiltViewModel<ProfileListTransactionsViewModel>()

            ProfileListTransactionsScreen(
                mainViewModel,
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
    }
}

enum class NamesHomeScreen(name: String) {
    Notification("Notification"),
}

enum class NamesProfileScreen(name: String) {
    ListTransactions("List Transactions"),
    Privacy("Privacy"),
    Payment("Payment"),
}