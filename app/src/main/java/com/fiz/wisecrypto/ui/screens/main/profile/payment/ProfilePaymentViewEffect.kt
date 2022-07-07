package com.fiz.wisecrypto.ui.screens.main.profile.payment

sealed class ProfilePaymentViewEffect {
    object MoveReturn : ProfilePaymentViewEffect()
    data class ShowToast(val message: Int) : ProfilePaymentViewEffect()
}