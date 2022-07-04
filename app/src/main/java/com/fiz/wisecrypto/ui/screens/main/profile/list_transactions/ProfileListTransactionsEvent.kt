package com.fiz.wisecrypto.ui.screens.main.profile.list_transactions

sealed class ProfileListTransactionsEvent {
    object BackButtonClicked : ProfileListTransactionsEvent()
    data class ChipClicked(val number: Int) : ProfileListTransactionsEvent()
}