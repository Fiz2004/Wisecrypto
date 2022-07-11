package com.fiz.wisecrypto.domain.models

import com.fiz.wisecrypto.ui.screens.main.models.CoinUi

data class Coin(
    val id: String = "",
    val icon: String = "",
    val abbreviated: String = "",
    val market: String = "",
    val name: String = "",
    val cost: Double = 0.0,
    val currentPrice: Double = 0.0,
    val priceChangePercentage: Double = 0.0,
) {
    fun toCoinUi(): CoinUi {
        return CoinUi(
            icon = icon,
            abbreviated = "$abbreviated/$market",
            name = name,
            cost = "\$${"%.2f".format(cost)}",
            up = priceChangePercentage > 0.0,
            value = "${"%.1f".format(priceChangePercentage)}%"
        )
    }
}