package com.fiz.wisecrypto.ui.screens.main.profile.privacy

sealed class ProfilePrivacyEvent {
    object BackButtonClicked : ProfilePrivacyEvent()
    object SaveButtonClicked : ProfilePrivacyEvent()
    data class EmailChanged(val value: String) : ProfilePrivacyEvent()
    data class NewPasswordChanged(val value: String) : ProfilePrivacyEvent()
    data class OldPasswordChanged(val value: String) : ProfilePrivacyEvent()
}