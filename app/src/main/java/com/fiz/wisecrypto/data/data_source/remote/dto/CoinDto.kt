package com.fiz.wisecrypto.data.data_source.remote.dto

import com.fiz.wisecrypto.domain.models.Coin
import com.google.gson.annotations.SerializedName

data class CoinDto(
    @SerializedName("id") val id: String? = "",
    @SerializedName("symbol") val symbol: String? = "",
    @SerializedName("name") val name: String? = "",
    @SerializedName("image") val image: String? = "",
    @SerializedName("current_price") val currentPrice: Double? = 0.0,
    @SerializedName("market_cap") val marketCap: Long? = 0L,
    @SerializedName("market_cap_rank") val marketCapRank: Int? = 0,
    @SerializedName("fully_diluted_valuation") val fullyDilutedValuation: Long? = 0,
    @SerializedName("total_volume") val totalVolume: Double? = 0.0,
    @SerializedName("high_24h") val high24h: Double? = 0.0,
    @SerializedName("low_24h") val low24h: Double? = 0.04,
    @SerializedName("price_change_24h") val priceChange24h: Double? = 0.0,
    @SerializedName("price_change_percentage_24h") val priceChangePercentage24h: Double? = 0.0,
    @SerializedName("market_cap_change_24h") val marketCapChange24h: Double? = 0.0,
    @SerializedName("market_cap_change_percentage_24h") val marketCapChangePercentage24h: Double? = 0.0,
    @SerializedName("circulating_supply") val circulatingSupply: Double? = 0.0,
    @SerializedName("total_supply") val totalSupply: Double? = 0.0,
    @SerializedName("max_supply") val maxSupply: Double? = 0.0,
    @SerializedName("ath") val ath: Double? = 0.0,
    @SerializedName("ath_change_percentage") val athChangePercentage: Double? = 0.0,
    @SerializedName("ath_date") val athDate: String? = "",
    @SerializedName("atl") val atl: Double? = 0.0,
    @SerializedName("atl_change_percentage") val atlChangePercentage: Double? = 0.0,
    @SerializedName("atl_date") val atlDate: String? = "",
    @SerializedName("roi") val roi: RoiDto? = null,
    @SerializedName("last_updated") val lastUpdated: String? = ""
) {
    fun toCoin(): Coin {
        return Coin(
            id = id.orEmpty(),
            icon = image.orEmpty(),
            symbol = symbol.orEmpty(),
            market = "BUSD",
            name = name.orEmpty(),
            currentPrice = currentPrice ?: 0.0,
            cost = currentPrice ?: 0.0,
            priceChangePercentage = priceChangePercentage24h ?: 0.0,
        )
    }
}

