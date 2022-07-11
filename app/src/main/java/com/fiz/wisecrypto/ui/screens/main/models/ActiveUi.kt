package com.fiz.wisecrypto.ui.screens.main.models

data class ActiveUi(
    val abbreviated: String = "",
    val name: String = "",
    val icon: String = "",
    val portfolio: String = "",
    val equivalent: String = "",
    val isUpDirectChangePercentage: Boolean = true,
    val changePercentage: String = "",
    val changeValue: String = ""
)