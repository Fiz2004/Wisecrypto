package com.fiz.wisecrypto.ui.screens.main.navigate.graphs

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.fiz.wisecrypto.ui.screens.main.navigate.names.NamesMainScreen

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
        homeGraph(navController, state)
        marketGraph(navController, state)
        profileGraph(navController, moveReturn)
    }
}
