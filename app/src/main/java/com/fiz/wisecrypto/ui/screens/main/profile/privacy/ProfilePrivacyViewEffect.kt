package com.fiz.wisecrypto.ui.screens.main.profile.privacy

sealed class ProfilePrivacyViewEffect {
    object MoveReturn : ProfilePrivacyViewEffect()
    data class ShowToast(val message: Int) : ProfilePrivacyViewEffect()
}