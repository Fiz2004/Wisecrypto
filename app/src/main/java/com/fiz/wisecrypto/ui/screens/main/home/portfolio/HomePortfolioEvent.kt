package com.fiz.wisecrypto.ui.screens.main.home.portfolio

sealed class HomePortfolioEvent {
    object BackButtonClicked : HomePortfolioEvent()
    object Started : HomePortfolioEvent()
    object Stopped : HomePortfolioEvent()
}