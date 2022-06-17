package com.fiz.wisecrypto.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var viewState by mutableStateOf(LoginViewState())
        private set


    fun onEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.StartScreen -> startScreen()
        }
    }

    private fun startScreen() {
        viewModelScope.launch {
            viewState = viewState.copy(isLoading = true)
            delay(3000)
            viewState = viewState.copy(isLoading = false)
        }
    }
}