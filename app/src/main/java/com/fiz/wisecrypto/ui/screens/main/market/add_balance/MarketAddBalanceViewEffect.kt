package com.fiz.wisecrypto.ui.screens.main.market.add_balance

sealed class MarketAddBalanceViewEffect {
    object MoveReturn : MarketAddBalanceViewEffect()
    data class ShowError(val message: String?) : MarketAddBalanceViewEffect()
}