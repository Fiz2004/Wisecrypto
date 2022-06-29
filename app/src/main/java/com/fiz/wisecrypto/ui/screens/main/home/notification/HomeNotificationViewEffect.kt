package com.fiz.wisecrypto.ui.screens.main.home.notification

sealed class HomeNotificationViewEffect {
    object MoveSignIn : HomeNotificationViewEffect()
    object MoveReturn : HomeNotificationViewEffect()
}