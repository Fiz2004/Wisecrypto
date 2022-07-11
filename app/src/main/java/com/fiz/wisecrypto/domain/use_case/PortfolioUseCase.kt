package com.fiz.wisecrypto.domain.use_case

import com.fiz.wisecrypto.data.repositories.UserRepositoryImpl
import com.fiz.wisecrypto.domain.models.Active
import com.fiz.wisecrypto.domain.models.Coin
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PortfolioUseCase @Inject constructor(
    private val userRepository: UserRepositoryImpl
) {
    fun getPricePortfolio(portfolio: List<Active>, coins: List<Coin>): Double {
        return portfolio.fold(0.0) { acc, active ->
            acc + active.count * (coins.first { it.id == active.id }.currentPrice)
        }
    }

    fun getPricePortfolioForBuy(portfolio: List<Active>): Double {
        return portfolio.fold(0.0) { acc, active ->
            acc + active.count * active.priceForBuy
        }
    }

    fun getChangePercentageBalance(portfolio: List<Active>, coins: List<Coin>): Double {
        val divided = getPricePortfolio(portfolio, coins) / getPricePortfolioForBuy(portfolio) * 100
        val changePercentageBalance = if (divided > 0) divided - 100 else 100 - divided
        return changePercentageBalance
    }
}