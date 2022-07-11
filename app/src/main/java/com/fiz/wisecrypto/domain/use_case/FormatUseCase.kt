package com.fiz.wisecrypto.domain.use_case

import javax.inject.Inject
import kotlin.math.abs

class FormatUseCase @Inject constructor() {
    fun getFormatPricePortfolio(pricePortfolio: Double): String {
        val currency = "\$"
        return "$currency${"%.2f".format(pricePortfolio)}"
    }

    fun getFormatTotalReturn(totalReturn: Double): String {
        val currency = "\$"
        return "$currency${"%.2f".format(totalReturn)}"
    }

    fun getFormatChangePercentagePricePortfolio(changePercentagePricePortfolio: Double): String {
        return "${"%.1f".format(abs(changePercentagePricePortfolio))}%"
    }

    fun getFormatBalanceUsd(balanceUsd: Double): String {
        return "%.0f".format(balanceUsd)
    }

    fun getFormatBalance(balance: Double): String {
        return "%.2f".format(balance)
    }
}