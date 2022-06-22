package com.fiz.wisecrypto.ui.screens.login.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.ui.screens.login.signin.SignUpEvent
import com.fiz.wisecrypto.ui.screens.login.signin.SignUpViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    var viewState by mutableStateOf(SignUpViewState())
        private set


    fun onEvent(event: SignUpEvent) {
        when (event) {
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