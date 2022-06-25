package com.fiz.wisecrypto.ui.screens.login.signup2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.data.repositories.UserRepositoryImpl
import com.fiz.wisecrypto.ui.screens.login.signup.SignUpViewModel
import com.fiz.wisecrypto.ui.screens.login.signup.SignUpViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUp2ViewModel @Inject constructor(private val userRepository: UserRepositoryImpl) :
    ViewModel() {

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
            is SignUp2Event.SignUpClicked -> signUpClicked(event.signUpViewState)
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

    private fun signUpClicked(signUpViewState: SignUpViewState) {
        viewModelScope.launch {
            if (viewState.privacy) {
                if (checkPasswordWithConfirmPassword(
                        viewState.password,
                        viewState.confirmPassword
                    )
                ) {
                    val fullName = signUpViewState.fullName
                    val numberPhone = signUpViewState.numberPhone
                    val userName = signUpViewState.userName
                    userRepository.saveUser(
                        fullName = fullName,
                        numberPhone = numberPhone,
                        userName = userName,
                        email = viewState.email,
                        password = viewState.password,
                    )
                }else{
                    viewEffect.emit(SignUp2ViewEffect.ShowError(R.string.signup2_error_noconfirm_password))
                }
                viewEffect.emit(SignUp2ViewEffect.MoveSignInScreen)
            }
        }
    }

    private fun checkPasswordWithConfirmPassword(
        password: String,
        confirmPassword: String
    ): Boolean {
        return password == confirmPassword
    }

    private fun confirmPasswordChanged(value: String) {
        viewState = viewState.copy(confirmPassword = value)
    }

}