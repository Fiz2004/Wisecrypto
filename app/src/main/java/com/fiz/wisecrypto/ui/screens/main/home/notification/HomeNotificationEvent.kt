package com.fiz.wisecrypto.ui.screens.main.home.notification

sealed class HomeNotificationEvent {
    object BackButtonClicked : HomeNotificationEvent()
}