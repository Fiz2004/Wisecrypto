package com.fiz.wisecrypto.domain.use_case

import com.fiz.wisecrypto.data.repositories.UserRepositoryImpl
import com.fiz.wisecrypto.domain.models.Coin
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PortfolioUseCase @Inject constructor(private val userRepository: UserRepositoryImpl) {
    fun getPricePortfolio(coins: List<Coin>): Double {
        return userRepository.portfolio.fold(0.0) { acc, active ->
            acc + active.count * (coins.first { it.id == active.id }.currentPrice)
        }
    }

    fun getPricePortfolioForBuy(): Double {
        return userRepository.portfolio.fold(0.0) { acc, active ->
            acc + active.count * active.priceForBuy
        }
    }

    fun getChangePercentageBalance(coins: List<Coin>): Double {
        val divided = getPricePortfolio(coins) / getPricePortfolioForBuy() * 100
        val changePercentageBalance = if (divided > 0) divided - 100 else 100 - divided
        return changePercentageBalance
    }
}