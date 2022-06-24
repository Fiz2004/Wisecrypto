package com.fiz.wisecrypto.ui.screens.main

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

    if (NamesMainScreen.values().any { it.name == currentScreen?.route })
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
    else {
        MainNavHost(
            navController = navController,
            modifier = Modifier,
            viewModel
        )
    }
}

enum class NamesMainScreen(name: String) {
    Home("Home"),
    Market("Market"),
    Profile("Profile"),
}

