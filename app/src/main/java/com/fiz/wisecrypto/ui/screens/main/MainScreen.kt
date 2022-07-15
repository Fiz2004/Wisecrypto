package com.fiz.wisecrypto.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fiz.wisecrypto.ui.screens.main.components.MainBottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    moveReturn: () -> Unit,
    state: LazyListState = rememberLazyListState(),
) {
    val navController = rememberNavController()
    val backstackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backstackEntry?.destination

    if (currentScreen?.route == NamesMainScreen.Home.name ||
        currentScreen?.route == NamesMainScreen.Market.name ||
        currentScreen?.route == NamesMainScreen.Profile.name
    )
        Scaffold(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .statusBarsPadding()
                .navigationBarsPadding(),
            bottomBar = {
                MainBottomBar(navController)
            },
            containerColor = MaterialTheme.colorScheme.surface
        ) { innerPadding ->
            MainNavHost(
                navController = navController,
                moveReturn = moveReturn,
                modifier = Modifier
                    .padding(innerPadding),
                state = state
            )
        }
    else {
        MainNavHost(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .statusBarsPadding()
                .navigationBarsPadding(),
            navController = navController,
            moveReturn = moveReturn,
            state = state
        )
    }
}



