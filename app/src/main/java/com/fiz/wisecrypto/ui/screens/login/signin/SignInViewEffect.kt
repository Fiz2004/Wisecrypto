package com.fiz.wisecrypto.ui.screens.login.signin

import com.fiz.wisecrypto.ui.screens.login.signup2.SignUp2ViewEffect

sealed class SignInViewEffect {
    object MoveForgotPasswordScreen : SignInViewEffect()
    object MoveSignUpScreen : SignInViewEffect()
    object MoveMainContentScreen : SignInViewEffect()
    data class ShowError(val textMessage: Int) : SignInViewEffect()
}