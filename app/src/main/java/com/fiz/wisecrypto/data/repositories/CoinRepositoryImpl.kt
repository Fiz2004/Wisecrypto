package com.fiz.wisecrypto.data.repositories

import com.fiz.wisecrypto.data.data_source.remote.CoingeckoApi
import com.fiz.wisecrypto.data.data_source.remote.dto.CoinDto
import com.fiz.wisecrypto.domain.models.Coin
import com.fiz.wisecrypto.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val coingeckoApi: CoingeckoApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    var coins: Response<List<CoinDto>>? = null

    suspend fun getCoins(): Resource<List<Coin>> {
        return withContext(dispatcher) {
            if (coins == null)
                coins = coingeckoApi.getCoins()
            if (coins?.isSuccessful == true)
                Resource.Success(coins?.body()?.map { it.toCoin() })
            else
                Resource.Error("Ошибка загрузки из сети")
        }
    }
}