package com.fiz.wisecrypto.ui.screens.login.signup2

import com.fiz.wisecrypto.ui.screens.login.signup.SignUpViewModel
import com.fiz.wisecrypto.ui.screens.login.signup.SignUpViewState

sealed class SignUp2Event {
    object SignInClicked : SignUp2Event()
    object PrivacyChanged : SignUp2Event()
    object TermsAndConditionsClicked : SignUp2Event()
    object PrivacyPolicyClicked : SignUp2Event()
    object ContentPolicyClicked : SignUp2Event()
    data class  SignUpClicked(val signUpViewState: SignUpViewState) : SignUp2Event()
    data class EmailChanged(val value: String) : SignUp2Event()
    data class PasswordChanged(val value: String) : SignUp2Event()
    data class ConfirmPasswordChanged(val value: String) : SignUp2Event()
}