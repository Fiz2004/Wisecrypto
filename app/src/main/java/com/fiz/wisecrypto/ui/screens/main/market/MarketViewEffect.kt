package com.fiz.wisecrypto.ui.screens.main.market

sealed class MarketViewEffect {
    data class ShowError(val message: String?) : MarketViewEffect()
    data class MoveHomeDetailScreen(val id: String) : MarketViewEffect()
}