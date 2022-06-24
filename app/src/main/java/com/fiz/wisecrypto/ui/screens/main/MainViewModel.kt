package com.fiz.wisecrypto.ui.screens.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
//    var viewState by mutableStateOf(SignInViewState())
//        private set
//
//    var viewEffect = MutableSharedFlow<SignInViewEffect>()
//        private set
//
//    fun onEvent(event: SignInEvent) {
//        when (event) {
//            is SignInEvent.EmailChanged -> emailChanged(event.value)
//            is SignInEvent.PasswordChanged -> passwordChanged(event.value)
//            SignInEvent.ForgotPasswordClicked -> forgotPasswordClicked()
//            SignInEvent.SignInClicked -> signInClicked()
//            SignInEvent.SignUpClicked -> signUpClicked()
//            SignInEvent.StartScreen -> startScreen()
//        }
//    }
//
//    private fun signUpClicked() {
//        viewModelScope.launch {
//            viewEffect.emit(SignInViewEffect.MoveSignUpScreen)
//        }
//    }
//
//    private fun signInClicked() {
//        viewModelScope.launch {
//            viewEffect.emit(SignInViewEffect.MoveMainContentScreen)
//        }
//    }
//
//    private fun passwordChanged(value: String) {
//        viewState = viewState.copy(password = value)
//    }
//
//    private fun forgotPasswordClicked() {
//        viewModelScope.launch {
//            viewEffect.emit(SignInViewEffect.MoveForgotPasswordScreen)
//        }
//    }
//
//    private fun emailChanged(value: String) {
//        viewState = viewState.copy(email = value)
//    }
//
//    private fun startScreen() {
//        viewModelScope.launch {
//            viewState = viewState.copy(isLoading = true)
//            delay(3000)
//            viewState = viewState.copy(isLoading = false)
//        }
//    }
}