package com.fiz.wisecrypto.ui.screens.login.signup

sealed class SignUpEvent {
    object SignInClicked : SignUpEvent()
    object NextButtonClicked : SignUpEvent()
    object SkipClicked : SignUpEvent()
    data class FullNameChanged(val value: String) : SignUpEvent()
    data class NumberPhoneChanged(val value: String) : SignUpEvent()
    data class UserNameChanged(val value: String) : SignUpEvent()
}