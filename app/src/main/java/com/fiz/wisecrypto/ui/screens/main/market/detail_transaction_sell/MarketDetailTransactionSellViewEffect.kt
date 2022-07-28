package com.fiz.wisecrypto.ui.screens.main.market.detail_transaction_sell

sealed class MarketDetailTransactionSellViewEffect {
    object MoveReturn : MarketDetailTransactionSellViewEffect()

    data class ShowError(val message: String?) : MarketDetailTransactionSellViewEffect()
}