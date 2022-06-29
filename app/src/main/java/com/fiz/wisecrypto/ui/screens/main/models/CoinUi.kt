package com.fiz.wisecrypto.ui.screens.main.models

data class CoinUi(
    val icon: Int,
    val abbreviated: String,
    val name: String,
    val cost: String,
    val up: Boolean,
    val value: String
)