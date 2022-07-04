package com.fiz.wisecrypto.ui.screens.main.profile.main

sealed class ProfileViewEffect {
    data class ShowError(val message: String) : ProfileViewEffect()
    object MovePullScreen : ProfileViewEffect()
    object MoveAddScreen : ProfileViewEffect()
    object MoveListTransactionsScreen : ProfileViewEffect()
    object MovePrivacyScreen : ProfileViewEffect()
    object MovePaymentScreen : ProfileViewEffect()
    object MoveNotificationsScreen : ProfileViewEffect()
    object MoveSignInScreen : ProfileViewEffect()
    object ShowChangeAvatarScreen : ProfileViewEffect()
}