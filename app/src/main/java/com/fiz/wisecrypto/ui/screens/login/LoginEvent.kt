package com.fiz.wisecrypto.ui.screens.login

sealed class LoginEvent {
    object StartScreen : LoginEvent()
}