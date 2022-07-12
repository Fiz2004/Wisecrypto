package com.fiz.wisecrypto.domain.models

data class CoinFull(
    val id: String = "",
    val symbol: String = "",
    val name: String = "",
    val localization: String = "",
    val image: String = "",
    val currentPrice: Double = 0.0,
    val ath: Double = 0.0,
    val atl: Double = 0.0,
    val marketCap: Double = 0.0,
    val totalVolume: Double = 0.0,
    val totalSupply: Double = 0.0,
    val maxSupply: Double? = null,
    val marketCapChange24h: Double = 0.0,
)