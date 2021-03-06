package com.fiz.wisecrypto.ui.screens.main.models

import com.fiz.wisecrypto.domain.models.Coin

data class CoinUi(
    val id: String = "",
    val icon: String = "",
    val abbreviated: String = "",
    val name: String = "",
    val cost: String = "",
    val up: Boolean = false,
    val value: String = ""
)

fun Coin.toCoinUi(): CoinUi {
    return CoinUi(
        id = id,
        icon = icon,
        abbreviated = "$symbol/$market",
        name = name,
        cost = "\$${"%.2f".format(cost)}",
        up = priceChangePercentage > 0.0,
        value = "${"%.1f".format(priceChangePercentage)}%"
    )
}