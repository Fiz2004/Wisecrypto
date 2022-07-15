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

    fun getFormatCoin(value: Double): String {
        return "%.6f".format(value)
    }

    fun getFormatOverview(price: Double?): String {
        val currency = "\$"
        if (price == null)
            return "∞"
        val value = abs(price)
        return when {
            value < 1.0 -> "$currency${"%.2f".format(price)}"
            value < 1_000_000.0 -> "$currency${"%.0f".format(price)}"
            value < 1_000_000_000.0 -> "$currency${"%.2f".format(price / 1_000_000)}M"
            value < 1_000_000_000_000.0 -> "$currency${"%.2f".format(price / 1_000_000_000)}B"
            value < 1_000_000_000_000_000.0 -> "$currency${"%.2f".format(price / 1_000_000_000_000)}T"
            else -> "$currency${"%.0f".format(value)}"
        }
    }
}