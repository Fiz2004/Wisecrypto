package com.fiz.wisecrypto.ui.screens.main.profile.notifications

sealed class ProfileNotificationsViewEffect {
    object MoveReturn : ProfileNotificationsViewEffect()
    data class ShowToast(val message: Int) : ProfileNotificationsViewEffect()
}