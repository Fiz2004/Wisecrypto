package com.fiz.wisecrypto.data.data_source.remote.dto

import com.fiz.wisecrypto.domain.models.CoinFull
import com.google.gson.annotations.SerializedName

data class CoinFullDto(
    @SerializedName("id") val id: String? = "",
    @SerializedName("symbol") val symbol: String? = "",
    @SerializedName("name") val name: String? = "",
    @SerializedName("localization") val localization: LocalizationDto? = null,
    @SerializedName("image") val image: ImageDTO? = null,
    @SerializedName("market_data") val marketData: MarketDataDTO? = null,
) {
    fun toCoinFull(): CoinFull {
        return CoinFull(
            id = id.orEmpty(),
            symbol = symbol.orEmpty(),
            name = name.orEmpty(),
            localization = localization?.ru.orEmpty(),
            image = image?.large.orEmpty(),
            currentPrice = marketData?.currentPrice?.usd ?: 0.0,
            ath = marketData?.ath?.usd ?: 0.0,
            atl = marketData?.atl?.usd ?: 0.0,
            marketCap = marketData?.marketCap?.usd ?: 0.0,
            totalVolume = marketData?.totalVolume?.usd ?: 0.0,
            totalSupply = marketData?.totalSupply ?: 0.0,
            maxSupply = marketData?.maxSupply,
            marketCapChange24h = marketData?.marketCapChange24h ?: 0.0,
        )
    }
}

data class LocalizationDto(
    @SerializedName("en") val en: String? = "",
    @SerializedName("ru") val ru: String? = "",
)

data class ImageDTO(
    @SerializedName("thumb") val thumb: String? = "",
    @SerializedName("small") val small: String? = "",
    @SerializedName("large") val large: String? = "",
)

data class MarketDataDTO(
    @SerializedName("current_price") val currentPrice: CurrentPriceDTO = CurrentPriceDTO(),
    @SerializedName("ath") val ath: AthDTO? = AthDTO(),
    @SerializedName("atl") val atl: AtlDTO? = AtlDTO(),
    @SerializedName("market_cap") val marketCap: MarketCapDTO? = MarketCapDTO(),
    @SerializedName("total_volume") val totalVolume: TotalVolumeDTO? = TotalVolumeDTO(),
    @SerializedName("total_supply") val totalSupply: Double? = 0.0,
    @SerializedName("max_supply") val maxSupply: Double? = 0.0,
    @SerializedName("market_cap_change_24h") val marketCapChange24h: Double? = 0.0,
)

data class CurrentPriceDTO(
    @SerializedName("usd") val usd: Double? = 0.0,
)

data class AthDTO(
    @SerializedName("usd") val usd: Double? = 0.0,
)

data class AtlDTO(
    @SerializedName("usd") val usd: Double? = 0.0,
)

data class MarketCapDTO(
    @SerializedName("usd") val usd: Double? = 0.0,
)

data class TotalVolumeDTO(
    @SerializedName("usd") val usd: Double? = 0.0,
)

