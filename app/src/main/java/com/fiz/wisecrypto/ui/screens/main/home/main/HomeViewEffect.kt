package com.fiz.wisecrypto.ui.screens.main.home.main

sealed class HomeViewEffect {
    object MoveSignIn : HomeViewEffect()
    object MoveNotificationScreen : HomeViewEffect()
    data class ShowError(val message: String) : HomeViewEffect()
}