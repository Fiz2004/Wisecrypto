package com.fiz.wisecrypto.data.data_source

import com.fiz.wisecrypto.domain.models.Coin

val coinsStore = listOf(
    Coin(
        icon = "",
        abbreviated = "BTC",
        market = "BUSD",
        name = "Bitcoin",
        cost = 54382.64,
        priceChange = 15.3
    ),
    Coin(
        icon = "",
        abbreviated = "ETH",
        market = "BUSD",
        name = "Etherium",
        cost = 4145.61,
        priceChange = -2.1
    ),
    Coin(
        icon = "",
        abbreviated = "LTC",
        market = "BUSD",
        name = "Litecoin",
        cost = 207.3,
        priceChange = -1.1
    ),
    Coin(
        icon = "",
        abbreviated = "SOL",
        market = "FIAT",
        name = "Solana",
        cost = 227.93,
        priceChange = 15.3
    ),
)