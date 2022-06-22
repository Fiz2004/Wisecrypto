package com.fiz.wisecrypto.ui.screens.login.signup

data class SignUpViewState(
    val fullName: String = "",
    val numberPhone: String = "",
    val userName: String = "",
    val isLoading: Boolean = false
)