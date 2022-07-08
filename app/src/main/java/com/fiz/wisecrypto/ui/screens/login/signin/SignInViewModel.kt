package com.fiz.wisecrypto.ui.screens.login.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.data.repositories.SettingsRepositoryImpl
import com.fiz.wisecrypto.data.repositories.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl,
    private val authRepository: SettingsRepositoryImpl
) : ViewModel() {

    var viewState by mutableStateOf(SignInViewState())
        private set

    var viewEffect = MutableSharedFlow<SignInViewEffect>()
        private set

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.EmailChanged -> emailChanged(event.value)
            is SignInEvent.PasswordChanged -> passwordChanged(event.value)
            SignInEvent.ForgotPasswordClicked -> forgotPasswordClicked()
            SignInEvent.SignInClicked -> signInClicked()
            SignInEvent.SignUpClicked -> signUpClicked()
        }
    }

    private fun signUpClicked() {
        viewModelScope.launch {
            viewEffect.emit(SignInViewEffect.MoveSignUpScreen)
        }
    }

    private fun signInClicked() {
        viewModelScope.launch {
            if (userRepository.checkUser(viewState.email, viewState.password) != null) {
                authRepository.authCompleted(viewState.email)
                viewEffect.emit(SignInViewEffect.MoveMainContentScreen)
            } else {
                viewEffect.emit(SignInViewEffect.ShowError(R.string.signin_error_signin))
            }

        }
    }

    private fun passwordChanged(value: String) {
        viewState = viewState.copy(password = value)
    }

    private fun forgotPasswordClicked() {
        viewModelScope.launch {
            viewEffect.emit(SignInViewEffect.MoveForgotPasswordScreen)
        }
    }

    private fun emailChanged(value: String) {
        viewState = viewState.copy(email = value)
    }

}