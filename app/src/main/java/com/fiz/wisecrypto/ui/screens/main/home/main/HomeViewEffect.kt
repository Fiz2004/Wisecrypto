package com.fiz.wisecrypto.ui.screens.main.home.main

sealed class HomeViewEffect {
    object MoveNotificationScreen : HomeViewEffect()
    object MoveHomePortfolioScreen : HomeViewEffect()
    data class ShowError(val message: String?) : HomeViewEffect()
    data class MoveHomeDetailScreen(val id: String) : HomeViewEffect()
}