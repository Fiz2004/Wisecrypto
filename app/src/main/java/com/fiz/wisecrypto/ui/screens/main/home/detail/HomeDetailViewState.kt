package com.fiz.wisecrypto.ui.screens.main.home.detail

import com.fiz.wisecrypto.domain.models.History
import com.fiz.wisecrypto.ui.screens.main.models.ActiveUi
import org.threeten.bp.LocalDateTime

data class HomeDetailViewState(
    val name: String = "",
    val isWatchList: Boolean = false,
    val priceOne: String = "",
    val abbreviated: String = "",
    val pricePortfolioIncreased: Boolean = true,
    val changePercentagePricePortfolio: String = "",
    val indexPeriodFilterChip: PeriodFilterChip = PeriodFilterChip.Day,
    val yourActive: ActiveUi? = null,

    val allTimeHigh: String = "",
    val allTimeLow: String = "",
    val marketCap: String = "",
    val totalVolume: String = "",
    val totalSupply: String = "",
    val maxSupply: String = "",
    val marketCapChange24h: String = "",
    val totalVolumeChange24h: String = "",

    val currentPriceHistoryValue: List<History> = listOf(),
    val currentPriceHistoryLabel: List<String> = listOf(),

    val isLoading: Boolean = false
)

enum class PeriodFilterChip {
    Day, Week, Month, Year
}