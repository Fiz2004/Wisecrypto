package com.fiz.wisecrypto.ui.screens.main.market.detail_transaction_add

sealed class MarketDetailTransactionAddViewEffect {
    object MoveReturn : MarketDetailTransactionAddViewEffect()

    data class ShowError(val message: String?) : MarketDetailTransactionAddViewEffect()
}