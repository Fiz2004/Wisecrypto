package com.fiz.wisecrypto.ui.screens.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.components.MainBottomBar
import com.fiz.wisecrypto.ui.screens.main.components.MainTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
) {
    val navController = rememberNavController()
    val backstackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backstackEntry?.destination

//    val state = viewModel.viewState

//    if (currentScreen?.route != NamesMainScreen.Home.name)
        Scaffold(
            topBar = {
                if (currentScreen?.route != NamesMainScreen.Home.name) {
                    MainTopAppBar(viewModel, currentScreen)
                }
            },
            bottomBar = {
                MainBottomBar(navController)
            },
            containerColor = MaterialTheme.colorScheme.surface
        ) { innerPadding ->
            MainNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                viewModel
            )
        }
//    else {
//        MainNavHost(
//            navController = navController,
//            modifier = Modifier,
//            viewModel
//        )
//    }
}

sealed class NamesMainScreen(
    val name: String,
    @StringRes val textId: Int,
    @DrawableRes val iconId: Int
) {
    object Home : NamesMainScreen("Home", R.string.screen_home, R.drawable.bottom_nav_ic_home)

    object Market :
        NamesMainScreen("Market", R.string.screen_market, R.drawable.bottom_nav_ic_market)

    object Profile :
        NamesMainScreen("Profile", R.string.screen_profile, R.drawable.bottom_nav_ic_profile)
}
