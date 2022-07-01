package com.fiz.wisecrypto.ui.screens.main.market

sealed class MarketEvent {
    data class SearchTextChanged(val value: String) : MarketEvent()
    data class MarketChipClicked(val index: Int) : MarketEvent()
    object Started : MarketEvent()
    object Stopped : MarketEvent()
}