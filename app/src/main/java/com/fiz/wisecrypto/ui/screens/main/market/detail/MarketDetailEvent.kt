package com.fiz.wisecrypto.ui.screens.main.market.detail

sealed class MarketDetailEvent {
    object BackButtonClicked : MarketDetailEvent()
    object AddWatchListClicked : MarketDetailEvent()
    object SellButtonClicked : MarketDetailEvent()
    object BuyButtonClicked : MarketDetailEvent()
    object Started : MarketDetailEvent()
    object Stopped : MarketDetailEvent()
    object OnRefresh : MarketDetailEvent()
    data class PeriodFilterChipClicked(val index: Int) : MarketDetailEvent()
}