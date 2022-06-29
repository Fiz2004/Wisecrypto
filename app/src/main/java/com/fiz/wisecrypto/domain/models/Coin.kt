package com.fiz.wisecrypto.domain.models

import com.fiz.wisecrypto.ui.screens.main.models.CoinUi

data class Coin(
    val icon: Int = 0,
    val abbreviated: String = "",
    val market: String = "",
    val name: String = "",
    val cost: Double = 0.0,
    val costLast: Double = 0.0,
) {
    fun toCoinUi(): CoinUi {
        val percent = if (costLast != 0.0) cost / costLast * 100.0 else 100.0
        val valuePercent = if (percent > 100.0) percent - 100 else 100 - percent

        val coinUi = CoinUi(
            icon = icon,
            abbreviated = "$abbreviated/$market",
            name = name,
            cost = "\$${cost}",
            up = cost > costLast,
            value = "${"%.1f".format(valuePercent)}%"
        )
        return coinUi
    }
}