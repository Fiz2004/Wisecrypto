package com.fiz.wisecrypto.ui.screens.main.profile.notifications

data class ProfileNotificationsViewState(
    val portfolioSwitchValue: Boolean = true,
    val popularSwitchValue: Boolean = true,
    val watchlistSwitchValue: Boolean = true,
    val promotionsSwitchValue: Boolean = true,
)