package com.fiz.wisecrypto.ui.screens.main.market.detail_transaction_cash

sealed class MarketDetailTransactionCashViewEffect {
    object MoveReturn : MarketDetailTransactionCashViewEffect()
    data class ShowError(val message: String?) : MarketDetailTransactionCashViewEffect()
}