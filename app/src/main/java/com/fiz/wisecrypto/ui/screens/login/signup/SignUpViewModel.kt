package com.fiz.wisecrypto.ui.screens.login.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {

    var viewState by mutableStateOf(SignUpViewState())
        private set

    var viewEffect = MutableSharedFlow<SignUpViewEffect>()
        private set

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.FullNameChanged -> fullNameChanged(event.value)
            SignUpEvent.NextButtonClicked -> nextButtonClicked()
            is SignUpEvent.NumberPhoneChanged -> numberPhoneChanged(event.value)
            SignUpEvent.SignInClicked -> signInClicked()
            is SignUpEvent.UserNameChanged -> userNameChanged(event.value)
            SignUpEvent.SkipClicked -> skipClicked()
        }
    }

    private fun skipClicked() {
        viewModelScope.launch {
            viewEffect.emit(SignUpViewEffect.MoveSignUpNextScreen)
        }
    }

    private fun userNameChanged(value: String) {
        viewState = viewState.copy(userName = value)
    }

    private fun signInClicked() {
        viewModelScope.launch {
            viewEffect.emit(SignUpViewEffect.MoveSignInScreen)
        }
    }

    private fun numberPhoneChanged(value: String) {
        viewState = viewState.copy(numberPhone = value)
    }

    private fun nextButtonClicked() {
        viewModelScope.launch {
            viewEffect.emit(SignUpViewEffect.MoveSignUpNextScreen)
        }
    }

    private fun fullNameChanged(value: String) {
        viewState = viewState.copy(fullName = value)
    }

}