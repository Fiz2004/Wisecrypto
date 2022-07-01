package com.fiz.wisecrypto.ui.screens.main.home.main

sealed class HomeEvent {
    object NotificationClicked : HomeEvent()
    object Started : HomeEvent()
    object Stopped : HomeEvent()
}