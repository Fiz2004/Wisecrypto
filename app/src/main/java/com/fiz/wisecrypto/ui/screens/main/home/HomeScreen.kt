package com.fiz.wisecrypto.ui.screens.main.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.ui.screens.main.MainViewModel

@Composable
fun HomeScreen(
    MainViewModel: MainViewModel = viewModel(),
    viewModel: HomeViewModel = viewModel()
) {
    Text("Home")
}