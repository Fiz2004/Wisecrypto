package com.fiz.wisecrypto.ui.screens.login.signup2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class SignUp2ViewModel : ViewModel() {

    var viewState by mutableStateOf(SignUp2ViewState())
        private set

    var viewEffect = MutableSharedFlow<SignUp2ViewEffect>()
        private set

    fun onEvent(event: SignUp2Event) {
        when (event) {
            SignUp2Event.SignInClicked -> signInClicked()
            is SignUp2Event.ConfirmPasswordChanged -> confirmPasswordChanged(event.value)
            is SignUp2Event.EmailChanged -> emailChanged(event.value)
            is SignUp2Event.PasswordChanged -> passwordChanged(event.value)
            SignUp2Event.PrivacyChanged -> privacyChanged()
            SignUp2Event.SignUpClicked -> signUpClicked()
            SignUp2Event.ContentPolicyClicked -> contentPolicyClicked()
            SignUp2Event.PrivacyPolicyClicked -> privacyPolicyClicked()
            SignUp2Event.TermsAndConditionsClicked -> termsAndConditionsClicked()
        }
    }

    private fun termsAndConditionsClicked() {
        viewModelScope.launch {
            viewEffect.emit(SignUp2ViewEffect.ShowTermsAndConditions)
        }
    }

    private fun privacyPolicyClicked() {
        viewModelScope.launch {
            viewEffect.emit(SignUp2ViewEffect.ShowPrivacyPolicy)
        }
    }

    private fun contentPolicyClicked() {
        viewModelScope.launch {
            viewEffect.emit(SignUp2ViewEffect.ShowContentPolicy)
        }
    }

    private fun privacyChanged() {
        val oldStatePrivacy = viewState.privacy
        viewState = viewState.copy(privacy = !oldStatePrivacy)
    }


    private fun emailChanged(value: String) {
        viewState = viewState.copy(email = value)
    }

    private fun signInClicked() {
        viewModelScope.launch {
            viewEffect.emit(SignUp2ViewEffect.MoveSignInScreen)
        }
    }

    private fun passwordChanged(value: String) {
        viewState = viewState.copy(password = value)
    }

    private fun signUpClicked() {
        viewModelScope.launch {
            viewEffect.emit(SignUp2ViewEffect.MoveSignInScreen)
        }
    }

    private fun confirmPasswordChanged(value: String) {
        viewState = viewState.copy(confirmPassword = value)
    }

}