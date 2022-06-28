package com.fiz.wisecrypto.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fiz.wisecrypto.ui.screens.main.home.HomeViewModel
import com.fiz.wisecrypto.ui.screens.main.home.main.HomeScreen
import com.fiz.wisecrypto.ui.screens.main.home.notification.HomeNotificationScreen
import com.fiz.wisecrypto.ui.screens.main.home.notification.HomeNotificationViewModel
import com.fiz.wisecrypto.ui.screens.main.market.MarketScreen
import com.fiz.wisecrypto.ui.screens.main.profile.ProfileScreen

@Composable
fun MainNavHost(
    navController: NavHostController,
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
            val viewModel = hiltViewModel<HomeViewModel>()

            MarketScreen(
                mainViewModel,
                viewModel,
            )
        }

        composable(NamesMainScreen.Profile.name) {
            val viewModel = hiltViewModel<HomeViewModel>()

            ProfileScreen(
                mainViewModel,
                viewModel,
            )
        }
    }
}

enum class NamesHomeScreen(name: String) {
    Notification("Notification"),
}