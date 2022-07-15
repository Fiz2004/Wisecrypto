package com.fiz.wisecrypto.data.repositories

import com.fiz.wisecrypto.data.data_source.remote.CoingeckoApi
import com.fiz.wisecrypto.domain.models.Coin
import com.fiz.wisecrypto.domain.models.CoinFull
import com.fiz.wisecrypto.domain.models.CoinMarketChartRange
import com.fiz.wisecrypto.ui.screens.main.market.detail.PeriodFilterChip
import com.fiz.wisecrypto.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
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

    suspend fun getCoin(id: String): Resource<CoinFull> {
        return withContext(dispatcher) {
            try {
                val coin = coingeckoApi.getCoinFull(id)
                Resource.Success(coin.toCoinFull())
            } catch (e: Exception) {
                Resource.Error(e.message)
            }
        }
    }

    suspend fun getCoinHistory(
        id: String,
        indexPeriodFilterChip: PeriodFilterChip
    ): Resource<CoinMarketChartRange> {
        return withContext(dispatcher) {
            val currentLocalDateTime = LocalDateTime.now()

            val yesterdayLocalDateTime =
                when (indexPeriodFilterChip) {
                    PeriodFilterChip.Day -> {
                        currentLocalDateTime.minusDays(1)
                    }
                    PeriodFilterChip.Week -> {
                        currentLocalDateTime.minusWeeks(1)
                    }
                    PeriodFilterChip.Month -> {
                        currentLocalDateTime.minusMonths(1)
                    }
                    PeriodFilterChip.Year -> {
                        currentLocalDateTime.minusYears(1)
                    }
                }
            val from: String = yesterdayLocalDateTime.atZone(ZoneId.systemDefault()).toInstant()
                .toEpochMilli()
                .toString().dropLast(3)

            val to: String = currentLocalDateTime.atZone(ZoneId.systemDefault()).toInstant()
                .toEpochMilli()
                .toString().dropLast(3)

            try {
                val coin = coingeckoApi.getCoinMarketChartRange(
                    id,
                    from,
                    to
                )
                Resource.Success(coin.toCoinMarketChartRange())
            } catch (e: Exception) {
                Resource.Error(e.message)
            }
        }
    }

    suspend fun getCoefCurrentToUsd(): Double {
        return coefCurrentToUsd
    }

}