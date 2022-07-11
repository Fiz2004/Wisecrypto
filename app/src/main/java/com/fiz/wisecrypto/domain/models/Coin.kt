package com.fiz.wisecrypto.domain.models

data class Coin(
    val id: String = "",
    val icon: String = "",
    val abbreviated: String = "",
    val market: String = "",
    val name: String = "",
    val cost: Double = 0.0,
    val currentPrice: Double = 0.0,
    val priceChangePercentage: Double = 0.0,
)