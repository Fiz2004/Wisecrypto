package com.fiz.wisecrypto.ui.screens.login.signup2

data class SignUp2ViewState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val privacy: Boolean = false,
    val isLoading: Boolean = false
)