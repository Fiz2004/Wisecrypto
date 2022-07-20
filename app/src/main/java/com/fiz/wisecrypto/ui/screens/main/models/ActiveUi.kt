package com.fiz.wisecrypto.ui.screens.main.models

import com.fiz.wisecrypto.domain.models.Active
import com.fiz.wisecrypto.domain.models.Coin
import kotlin.math.abs

data class ActiveUi(
    val id: String = "",
    val abbreviated: String = "",
    val name: String = "",
    val icon: String = "",
    val portfolio: String = "",
    val equivalent: String = "",
    val pricePortfolioIncreased: Boolean = true,
    val changePercentage: String = "",
    val changeValue: String = ""
)

fun Active.toActiveUi(coins: List<Coin>): ActiveUi {
    val current = coins.find { it.id == id } ?: return ActiveUi()

    val divided = (current.currentPrice / priceForBuy) * 100
    val changed = countUi * (current.currentPrice - priceForBuy)
    val percent = if (divided > 0) divided - 100 else 100 - divided
    val portfolio = countUi * current.currentPrice
    val changeValue = if (changed > 0)
        "+ $${"%.1f".format(abs(changed))}"
    else
        "- $${"%.1f".format(abs(changed))}"
    return ActiveUi(
        id = current.id,
        abbreviated = current.symbol.uppercase(),
        name = current.name,
        icon = current.icon,
        portfolio = "\$${"%.2f".format(portfolio)}",
        equivalent = "${"%.4f".format(countUi)} ${current.symbol.uppercase()}",
        pricePortfolioIncreased = percent > 0.0,
        changePercentage = "${"%.1f".format(percent)}%",
        changeValue = changeValue
    )
}