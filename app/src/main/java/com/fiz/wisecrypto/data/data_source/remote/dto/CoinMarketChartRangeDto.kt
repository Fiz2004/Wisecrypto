package com.fiz.wisecrypto.data.data_source.remote.dto

import com.fiz.wisecrypto.domain.models.CoinMarketChartRange
import com.fiz.wisecrypto.domain.models.History
import com.google.gson.annotations.SerializedName
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

data class CoinMarketChartRangeDto(
    @SerializedName("prices") val prices: List<List<String>>? = null,
    @SerializedName("market_caps") val marketCaps: List<List<String>>? = null,
    @SerializedName("total_volumes") val totalVolumes: List<List<String>>? = null,
) {
    fun toCoinMarketChartRange(): CoinMarketChartRange {
        return CoinMarketChartRange(
            prices = prices?.map(::toHistory) ?: listOf(),
            marketCaps = marketCaps?.map(::toHistory) ?: listOf(),
            totalVolumes = totalVolumes?.map(::toHistory) ?: listOf(),
        )
    }

    private fun toHistory(it: List<String>) =
        History(epochMilliToLocalDataTime(it[0].toLong()), it[1].toDouble())

    private fun epochMilliToLocalDataTime(it: Long): LocalDateTime =
        LocalDateTime.ofInstant(
            Instant.ofEpochMilli(it),
            ZoneOffset.systemDefault()
        )
}