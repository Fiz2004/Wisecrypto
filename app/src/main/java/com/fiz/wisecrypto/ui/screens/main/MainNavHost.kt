package com.fiz.wisecrypto.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fiz.wisecrypto.ui.screens.main.home.detail.HomeDetailScreen
import com.fiz.wisecrypto.ui.screens.main.home.detail.HomeDetailViewModel
import com.fiz.wisecrypto.ui.screens.main.home.main.HomeScreen
import com.fiz.wisecrypto.ui.screens.main.home.main.HomeViewModel
import com.fiz.wisecrypto.ui.screens.main.home.notification.HomeNotificationScreen
import com.fiz.wisecrypto.ui.screens.main.home.notification.HomeNotificationViewModel
import com.fiz.wisecrypto.ui.screens.main.home.portfolio.HomePortfolioScreen
import com.fiz.wisecrypto.ui.screens.main.home.portfolio.HomePortfolioViewModel
import com.fiz.wisecrypto.ui.screens.main.market.MarketScreen
import com.fiz.wisecrypto.ui.screens.main.market.MarketViewModel
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

@Composable
fun MainNavHost(
    navController: NavHostController,
    moveReturn: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = NamesMainScreen.Home.name,
        modifier = modifier
    ) {
        composable(NamesMainScreen.Home.name) {
            val viewModel = hiltViewModel<HomeViewModel>()

            HomeScreen(
                viewModel,
                moveNotificationScreen = { navController.navigate(NamesHomeScreen.Notification.name) },
                moveHomePortfolioScreen = { navController.navigate(NamesHomeScreen.Portfolio.name) },
                moveHomeDetailScreen = { id -> navController.navigate(NamesHomeScreen.Detail.name + "/$id") }
            )
        }

        composable(NamesHomeScreen.Notification.name) {
            val viewModel = hiltViewModel<HomeNotificationViewModel>()

            HomeNotificationScreen(
                viewModel,
                moveHomeMain = { navController.popBackStack() }
            )
        }

        composable(NamesHomeScreen.Portfolio.name) {
            val viewModel = hiltViewModel<HomePortfolioViewModel>()

            HomePortfolioScreen(
                viewModel,
                moveHomeMainScreen = { navController.popBackStack() },
                moveHomeDetailScreen = { id -> navController.navigate(NamesHomeScreen.Detail.name + "/$id") }
            )
        }

        composable(NamesHomeScreen.Detail.name + "/{id}") {
            val viewModel = hiltViewModel<HomeDetailViewModel>()
            val id = it.arguments?.getString("id") ?: return@composable

            HomeDetailScreen(
                viewModel,
                id,
                moveHomeMainScreen = { navController.popBackStack() },
            )
        }

        composable(NamesMainScreen.Market.name) {
            val viewModel = hiltViewModel<MarketViewModel>()

            MarketScreen(
                viewModel,
                moveHomeDetailScreen = { id -> navController.navigate(NamesHomeScreen.Detail.name + "/$id") }
            )
        }

        composable(NamesMainScreen.Profile.name) {
            val viewModel = hiltViewModel<ProfileViewModel>()

            ProfileScreen(
                viewModel,
                movePullScreen = {},
                moveAddScreen = {},
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

enum class NamesHomeScreen {
    Notification,
    Portfolio,
    Detail
}

enum class NamesProfileScreen {
    ListTransactions,
    Privacy,
    Payment,
    Notifications,
}