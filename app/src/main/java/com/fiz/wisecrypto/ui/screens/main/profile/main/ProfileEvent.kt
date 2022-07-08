package com.fiz.wisecrypto.ui.screens.main.profile.main

sealed class ProfileEvent {
    object ListTransactionsClicked : ProfileEvent()
    object PrivacyClicked : ProfileEvent()
    object PaymentClicked : ProfileEvent()
    object NotificationsClicked : ProfileEvent()
    object ProfileExitClicked : ProfileEvent()
    object PullClicked : ProfileEvent()
    object AddClicked : ProfileEvent()
    object ChangeAvatarClicked : ProfileEvent()
    object ConfirmExitClicked : ProfileEvent()
    object CancelExitClicked : ProfileEvent()
}