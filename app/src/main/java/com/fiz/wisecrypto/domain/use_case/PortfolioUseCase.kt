package com.fiz.wisecrypto.domain.use_case

import com.fiz.wisecrypto.domain.models.Active
import com.fiz.wisecrypto.domain.models.Coin
import com.fiz.wisecrypto.ui.screens.main.models.PortfolioUI
import com.fiz.wisecrypto.ui.screens.main.models.toActiveUi
import javax.inject.Inject

class PortfolioUseCase @Inject constructor(
    private val formatUseCase: FormatUseCase
) {

    fun getPortfolioUi(actives: List<Active>, coins: List<Coin>): PortfolioUI {
        val pricePortfolio = getPricePortfolio(
            actives,
            coins
        )
        val formatPricePortfolio = formatUseCase.getFormatPricePortfolio(pricePortfolio)
        val pricePortfolioForBuy =
            getPricePortfolioForBuy(actives)
        val changePercentagePricePortfolio =
            getChangePercentagePricePortfolio(
                actives,
                coins
            )
        val formatChangePercentagePricePortfolio =
            formatUseCase.getFormatChangePercentagePricePortfolio(changePercentagePricePortfolio)
        val totalReturn = pricePortfolio - pricePortfolioForBuy
        val formatTotalReturn = formatUseCase.getFormatTotalReturn(totalReturn)
        return PortfolioUI(
            actives = actives.map { it.toActiveUi(coins) },
            balancePortfolio = formatPricePortfolio,
            isPricePortfolioIncreased = changePercentagePricePortfolio >= 0.0,
            percentageChangedBalance = formatChangePercentagePricePortfolio,
            totalReturn = formatTotalReturn
        )
    }

    private fun getPricePortfolio(actives: List<Active>, coins: List<Coin>): Double {
        return actives.fold(0.0) { acc, active ->
            acc + (active.countUi) * (coins.first { it.id == active.id }.currentPrice)
        }
    }

    private fun getPricePortfolioForBuy(portfolio: List<Active>): Double {
        return portfolio.fold(0.0) { acc, active ->
            acc + active.countUi * active.priceForBuy
        }
    }

    private fun getChangePercentagePricePortfolio(
        portfolio: List<Active>,
        coins: List<Coin>
    ): Double {
        if (portfolio.isEmpty())
            return 0.0
        val divided = getPricePortfolio(portfolio, coins) / getPricePortfolioForBuy(portfolio) * 100
        val changePercentageBalance = if (divided > 0) divided - 100 else 100 - divided
        return changePercentageBalance
    }

}