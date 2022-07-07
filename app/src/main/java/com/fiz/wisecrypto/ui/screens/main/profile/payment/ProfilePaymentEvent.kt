package com.fiz.wisecrypto.ui.screens.main.profile.payment

sealed class ProfilePaymentEvent {
    object BackButtonClicked : ProfilePaymentEvent()
    object AddPayButtonClicked : ProfilePaymentEvent()
    data class DeletePayIconClicked(val index: Int) : ProfilePaymentEvent()
}