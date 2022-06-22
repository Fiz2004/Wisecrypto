package com.fiz.wisecrypto.ui.screens.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    var viewState by mutableStateOf(SplashViewState())
        private set

    var viewEffect = MutableSharedFlow<SplashViewEffect>()
        private set

    fun onEvent(event: SplashEvent) {
        when (event) {
            SplashEvent.StartScreen -> startScreen()
        }
    }

    private fun startScreen() {
        viewModelScope.launch {
            viewState = viewState.copy(isLoading = true)
            delay(3000)
            viewState = viewState.copy(isLoading = false)
            viewEffect.emit(SplashViewEffect.MoveNextScreen)
        }
    }
}

