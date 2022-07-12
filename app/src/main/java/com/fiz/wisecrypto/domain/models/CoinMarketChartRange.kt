package com.fiz.wisecrypto.domain.models

import org.threeten.bp.LocalDateTime

data class CoinMarketChartRange(
    val prices: List<History> = listOf(),
    val marketCaps: List<History> = listOf(),
    val totalVolumes: List<History> = listOf(),
)

data class History(
    val time: LocalDateTime,
    val value: Double
)