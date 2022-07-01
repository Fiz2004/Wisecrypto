package com.fiz.wisecrypto.data.data_source.remote

import com.fiz.wisecrypto.data.data_source.remote.dto.CoinDto
import retrofit2.http.GET

interface CoingeckoApi {

    @GET("coins/markets?vs_currency=usd")
    suspend fun getCoins(): List<CoinDto>

    companion object {
        const val BASE_URL = "https://api.coingecko.com/api/v3/"
    }
}