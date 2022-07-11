package com.fiz.wisecrypto.data.repositories

import com.fiz.wisecrypto.data.data_source.remote.CoingeckoApi
import com.fiz.wisecrypto.domain.models.Coin
import com.fiz.wisecrypto.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val coefCurrentToUsd = 1 / 52.0

class CoinRepositoryImpl @Inject constructor(
    private val coingeckoApi: CoingeckoApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend fun getCoins(): Resource<List<Coin>> {
        return withContext(dispatcher) {
            try {
                val coins = coingeckoApi.getCoins()
                Resource.Success(coins.map { it.toCoin() })
            } catch (e: Exception) {
                Resource.Error(e.message)
            }
        }
    }

    suspend fun getCoefCurrentToUsd(): Double {
        return coefCurrentToUsd
    }

}