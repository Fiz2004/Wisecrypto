package com.fiz.wisecrypto.ui.screens.login.signin

sealed class SignInEvent {
    object ForgotPasswordClicked : SignInEvent()
    object SignInClicked : SignInEvent()
    object SignUpClicked : SignInEvent()
    data class EmailChanged(val value: String) : SignInEvent()
    data class PasswordChanged(val value: String) : SignInEvent()
}