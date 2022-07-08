package com.fiz.wisecrypto.ui.screens.main.profile.notifications

sealed class ProfileNotificationsEvent {
    object BackButtonClicked : ProfileNotificationsEvent()
    object PortfolioSwitchClicked : ProfileNotificationsEvent()
    object PopularSwitchClicked : ProfileNotificationsEvent()
    object WatchlistSwitchClicked : ProfileNotificationsEvent()
    object PromotionsSwitchClicked : ProfileNotificationsEvent()
    object SaveButtonClicked : ProfileNotificationsEvent()
}