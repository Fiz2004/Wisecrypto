package com.fiz.wisecrypto.ui.screens.main.home.portfolio

sealed class HomePortfolioEvent {
    object BackButtonClicked : HomePortfolioEvent()
    data class YourActiveClicked(val id: String) : HomePortfolioEvent()
}