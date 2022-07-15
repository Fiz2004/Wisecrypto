package com.fiz.wisecrypto.ui.screens.main.home.portfolio

import com.fiz.wisecrypto.ui.screens.main.home.main.HomeEvent

sealed class HomePortfolioEvent {
    object BackButtonClicked : HomePortfolioEvent()
    object Started : HomePortfolioEvent()
    object Stopped : HomePortfolioEvent()
    object OnRefresh : HomePortfolioEvent()
    data class YourActiveClicked(val id: String) : HomePortfolioEvent()
}