package com.fiz.wisecrypto.domain.models

import com.fiz.wisecrypto.ui.screens.main.models.ActiveUi

data class Active(
    val id: String = "",
    val count: Double = 0.0,
    val priceForBuy: Double = 0.0
) {
    fun toActiveUi(data: List<Coin>?): ActiveUi {
        val current = data?.first { it.id == id } ?: return ActiveUi()
        val divided = current.currentPrice / priceForBuy * 100
        val changed = count * (current.currentPrice - priceForBuy)
        val percent = if (divided > 0) divided - 100 else 100 - divided
        val portfolio = count * (current.currentPrice)
        val changeValue = if (changed > 0)
            "+ $${"%.1f".format(changed)}"
        else
            "- $${"%.1f".format(changed)}"
        return ActiveUi(
            abbreviated = current.abbreviated.uppercase(),
            name = current.name,
            icon = current.icon,
            portfolio = "\$${"%.2f".format(portfolio)}",
            equivalent = "${"%.4f".format(count)} ${current.abbreviated.uppercase()}",
            isUpDirectChangePercentage = percent > 0.0,
            changePercentage = "${"%.1f".format(percent)}%",
            changeValue = changeValue
        )
    }
}