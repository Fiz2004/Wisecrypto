package com.fiz.wisecrypto.data.data_source

import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.domain.models.Coin

val coinsStore = listOf(
    Coin(
        icon = R.drawable.home_pic_bitcoin,
        abbreviated = "BTC",
        market = "BUSD",
        name = "Bitcoin",
        cost = 54382.64,
        costLast = 46062.09
    ),
    Coin(
        icon = R.drawable.home_pic_etherium,
        abbreviated = "ETH",
        market = "BUSD",
        name = "Etherium",
        cost = 4145.61,
        costLast = 4232.68
    ),
    Coin(
        icon = R.drawable.home_pic_litecoin,
        abbreviated = "LTC",
        market = "BUSD",
        name = "Litecoin",
        cost = 207.3,
        costLast = 209.58
    ),
    Coin(
        icon = R.drawable.home_pic_solana,
        abbreviated = "SOL",
        market = "FIAT",
        name = "Solana",
        cost = 227.93,
        costLast = 193.06
    ),
)