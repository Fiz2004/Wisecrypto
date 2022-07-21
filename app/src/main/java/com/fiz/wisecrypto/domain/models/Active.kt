package com.fiz.wisecrypto.domain.models

class Active private constructor(
    val id: String = "",
    private var count: Long = 0,
    var priceForBuy: Double = 0.0
) {
    val countUi
        get() = count / COIN_ACCURACY

    val countForSaveEntity
        get() = count

    val isEmpty
        get() = count == 0L

    fun sell(count: Double) {
        this.count -= (count * COIN_ACCURACY).toLong()
    }

    fun buy(count: Double, price: Double) {
        val predAllPrice = countUi * priceForBuy
        this.count += (count * COIN_ACCURACY).toLong()
        priceForBuy = (predAllPrice + count * price) / countUi
    }

    companion object {
        private const val COIN_ACCURACY = 1E6

        fun create(id: String, count: Double, priceForBuy: Double): Active {
            return Active(
                id = id,
                count = (count * COIN_ACCURACY).toLong(),
                priceForBuy = priceForBuy
            )
        }

        fun create(id: String, count: Long, priceForBuy: Double): Active {
            return Active(
                id = id,
                count = count,
                priceForBuy = priceForBuy
            )
        }

    }
}

