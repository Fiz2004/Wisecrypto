package com.fiz.wisecrypto.ui.screens.main.profile.main

data class ProfileViewState(
    val fullName: String = "",
    val photo: String = "",
    val balanceCurrentCurrency: String = "",
    val balanceUsd: String = "",
    val isLoading: Boolean = false
)