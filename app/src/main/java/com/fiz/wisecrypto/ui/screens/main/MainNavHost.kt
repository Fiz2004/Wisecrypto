package com.fiz.wisecrypto.ui.screens.main

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.home.main.HomeScreen
import com.fiz.wisecrypto.ui.screens.main.home.main.HomeViewModel
import com.fiz.wisecrypto.ui.screens.main.home.notification.HomeNotificationScreen
import com.fiz.wisecrypto.ui.screens.main.home.notification.HomeNotificationViewModel
import com.fiz.wisecrypto.ui.screens.main.home.portfolio.HomePortfolioScreen
import com.fiz.wisecrypto.ui.screens.main.home.portfolio.HomePortfolioViewModel
import com.fiz.wisecrypto.ui.screens.main.market.add_balance.MarketAddBalanceScreen
import com.fiz.wisecrypto.ui.screens.main.market.add_balance.MarketAddBalanceViewModel
import com.fiz.wisecrypto.ui.screens.main.market.buy.MarketBuyScreen
import com.fiz.wisecrypto.ui.screens.main.market.buy.MarketBuyViewModel
import com.fiz.wisecrypto.ui.screens.main.market.cash_balance.MarketCashBalanceScreen
import com.fiz.wisecrypto.ui.screens.main.market.cash_balance.MarketCashBalanceViewModel
import com.fiz.wisecrypto.ui.screens.main.market.detail.MarketDetailScreen
import com.fiz.wisecrypto.ui.screens.main.market.detail.MarketDetailViewModel
import com.fiz.wisecrypto.ui.screens.main.market.main.MarketMainScreen
import com.fiz.wisecrypto.ui.screens.main.market.main.MarketViewModel
import com.fiz.wisecrypto.ui.screens.main.market.sell.MarketSellScreen
import com.fiz.wisecrypto.ui.screens.main.market.sell.MarketSellViewModel
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
    state: LazyListState = rememberLazyListState(),
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
                moveMarketDetailScreen = { id -> navController.navigate(NamesMarketScreen.Detail.name + "/$id") },
                moveMarketAddBalanceScreen = { navController.navigate(NamesMarketScreen.AddBalance.name) }
            )
        }

        composable(NamesHomeScreen.Notification.name) {
            val viewModel = hiltViewModel<HomeNotificationViewModel>()

            HomeNotificationScreen(
                viewModel,
                moveReturn = { navController.popBackStack() }
            )
        }

        composable(NamesHomeScreen.Portfolio.name) {
            val viewModel = hiltViewModel<HomePortfolioViewModel>()

            HomePortfolioScreen(
                viewModel,
                moveReturn = { navController.popBackStack() },
                moveMarketDetailScreen = { id -> navController.navigate(NamesMarketScreen.Detail.name + "/$id") }
            )
        }

        composable(NamesMarketScreen.Detail.name + "/{id}") {
            val viewModel = hiltViewModel<MarketDetailViewModel>()
            val id = it.arguments?.getString("id") ?: return@composable
            viewModel.idCoin = id
            viewModel.monthNames = stringArrayResource(id = R.array.month_3chars).toList()
            viewModel.daysNames = stringArrayResource(id = R.array.day_of_week_2chars).toList()

            MarketDetailScreen(
                viewModel,
                moveReturnScreen = { navController.popBackStack() },
                moveMarketSellScreen = { navController.navigate(NamesMarketScreen.Sell.name + "/$id") },
                moveMarketBuyScreen = { navController.navigate(NamesMarketScreen.Buy.name + "/$id") }
            )
        }

        composable(NamesMarketScreen.Sell.name + "/{id}") {
            val viewModel = hiltViewModel<MarketSellViewModel>()
            val id = it.arguments?.getString("id") ?: return@composable
            viewModel.idCoin = id

            MarketSellScreen(
                viewModel,
                moveReturn = { navController.popBackStack() },
            )
        }

        composable(NamesMarketScreen.Buy.name + "/{id}") {
            val viewModel = hiltViewModel<MarketBuyViewModel>()
            val id = it.arguments?.getString("id") ?: return@composable
            viewModel.idCoin = id

            MarketBuyScreen(
                viewModel,
                moveReturn = { navController.popBackStack() },
                moveMarketAddBalanceScreen = { navController.navigate(NamesMarketScreen.AddBalance.name) }
            )
        }

        composable(NamesMarketScreen.AddBalance.name) {
            val viewModel = hiltViewModel<MarketAddBalanceViewModel>()

            MarketAddBalanceScreen(
                viewModel,
                moveReturn = { navController.popBackStack() },
            )
        }

        composable(NamesMarketScreen.CashBalance.name) {
            val viewModel = hiltViewModel<MarketCashBalanceViewModel>()

            MarketCashBalanceScreen(
                viewModel,
                moveReturn = { navController.popBackStack() },
            )
        }

        composable(NamesMainScreen.Market.name) {
            val viewModel = hiltViewModel<MarketViewModel>()

            MarketMainScreen(
                viewModel,
                state = state,
                moveMarketDetailScreen = { id -> navController.navigate(NamesMarketScreen.Detail.name + "/$id") }
            )
        }

        composable(NamesMainScreen.Profile.name) {
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

enum class NamesHomeScreen {
    Notification,
    Portfolio,
}

enum class NamesMarketScreen {
    Detail,
    Sell,
    Buy,
    AddBalance,
    CashBalance
}

enum class NamesProfileScreen {
    ListTransactions,
    Privacy,
    Payment,
    Notifications,
}