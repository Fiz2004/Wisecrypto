package com.fiz.wisecrypto.ui.screens.login.signup

sealed class SignUpViewEffect {
    object MoveSignUpNextScreen : SignUpViewEffect()
    object MoveSignInScreen : SignUpViewEffect()
}