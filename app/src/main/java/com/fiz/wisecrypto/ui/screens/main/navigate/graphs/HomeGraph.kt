package com.fiz.wisecrypto.ui.screens.main.navigate.graphs

import androidx.compose.foundation.lazy.LazyListState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fiz.wisecrypto.ui.screens.main.home.main.HomeScreen
import com.fiz.wisecrypto.ui.screens.main.home.main.HomeViewModel
import com.fiz.wisecrypto.ui.screens.main.home.notification.HomeNotificationScreen
import com.fiz.wisecrypto.ui.screens.main.home.notification.HomeNotificationViewModel
import com.fiz.wisecrypto.ui.screens.main.home.portfolio.HomePortfolioScreen
import com.fiz.wisecrypto.ui.screens.main.home.portfolio.HomePortfolioViewModel
import com.fiz.wisecrypto.ui.screens.main.navigate.names.NamesHomeScreen
import com.fiz.wisecrypto.ui.screens.main.navigate.names.NamesMainScreen
import com.fiz.wisecrypto.ui.screens.main.navigate.names.NamesMarketScreen

fun NavGraphBuilder.homeGraph(navController: NavHostController, state: LazyListState) {
    navigation(
        startDestination = NamesHomeScreen.HomeMain.name,
        route = NamesMainScreen.Home.name
    ) {
        composable(NamesHomeScreen.HomeMain.name) {
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

    }
}