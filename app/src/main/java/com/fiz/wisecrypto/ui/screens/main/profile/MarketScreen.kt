package com.fiz.wisecrypto.ui.screens.main.profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.ui.screens.main.MainViewModel
import com.fiz.wisecrypto.ui.screens.main.home.main.HomeViewModel

@Composable
fun ProfileScreen(
    MainViewModel: MainViewModel = viewModel(),
    viewModel: HomeViewModel = viewModel()
) {
    Text("Profile")
}