package com.fiz.wisecrypto.ui.screens.main.home.main

sealed class HomeEvent {
    object NotificationClicked : HomeEvent()
    object YourActiveAllClicked : HomeEvent()
    data class YourActiveClicked(val id: String) : HomeEvent()
    object Started : HomeEvent()
    object Stopped : HomeEvent()
}