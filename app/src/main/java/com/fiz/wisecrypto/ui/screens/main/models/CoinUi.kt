package com.fiz.wisecrypto.ui.screens.main.models

data class CoinUi(
    val icon: String,
    val abbreviated: String,
    val name: String,
    val cost: String,
    val up: Boolean,
    val priceChange: String,
    val value: String
)