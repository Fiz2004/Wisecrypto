package com.fiz.wisecrypto.ui.screens.login.signin

sealed class SignInViewEffect {
    object MoveForgotPasswordScreen : SignInViewEffect()
    object MoveSignUpScreen : SignInViewEffect()
    object MoveMainContentScreen : SignInViewEffect()
}