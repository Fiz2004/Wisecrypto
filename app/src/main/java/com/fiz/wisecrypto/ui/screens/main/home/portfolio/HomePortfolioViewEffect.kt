package com.fiz.wisecrypto.ui.screens.main.home.portfolio

sealed class HomePortfolioViewEffect {
    object MoveReturn : HomePortfolioViewEffect()
    data class ShowError(val message: String?) : HomePortfolioViewEffect()
}