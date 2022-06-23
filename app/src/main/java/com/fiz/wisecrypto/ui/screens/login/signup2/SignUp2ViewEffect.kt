package com.fiz.wisecrypto.ui.screens.login.signup2

sealed class SignUp2ViewEffect {
    object MoveSignInScreen : SignUp2ViewEffect()
    object ShowTermsAndConditions : SignUp2ViewEffect()
    object ShowPrivacyPolicy : SignUp2ViewEffect()
    object ShowContentPolicy : SignUp2ViewEffect()
}