package com.fiz.wisecrypto.ui.screens.main.components

import androidx.compose.material3.SmallTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import com.fiz.wisecrypto.ui.screens.main.MainViewModel


@Composable
fun MainTopAppBar(
    viewModel: MainViewModel = viewModel(),
    currentScreen: NavDestination?,
) {
//    val state = viewModel.viewState

    SmallTopAppBar(
        modifier = Modifier,
        title = { }
    )

}