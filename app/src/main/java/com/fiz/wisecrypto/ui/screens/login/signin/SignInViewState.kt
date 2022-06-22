package com.fiz.wisecrypto.ui.screens.login.signin

data class SignInViewState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false
)