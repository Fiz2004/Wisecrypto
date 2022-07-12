package com.fiz.wisecrypto.data.data_source.remote

import com.fiz.wisecrypto.data.data_source.remote.dto.CoinDto
import com.fiz.wisecrypto.data.data_source.remote.dto.CoinFullDto
import com.fiz.wisecrypto.data.data_source.remote.dto.CoinMarketChartRangeDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoingeckoApi {

    @GET("coins/markets?vs_currency=usd")
    suspend fun getCoins(): List<CoinDto>

    @GET("coins/{id}")
    suspend fun getCoinFull(@Path("id") id: String): CoinFullDto

    @GET("coins/{id}/market_chart/range?vs_currency=usd")
    suspend fun getCoinMarketChartRange(
        @Path("id") id: String,
        @Query("from") from: String,
        @Query("to") to: String
    ): CoinMarketChartRangeDto

    companion object {
        const val BASE_URL = "https://api.coingecko.com/api/v3/"
    }
}