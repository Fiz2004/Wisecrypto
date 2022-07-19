package com.fiz.wisecrypto.ui.screens.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.BuildConfig
import com.fiz.wisecrypto.data.repositories.SettingsRepositoryImpl
import com.fiz.wisecrypto.data.repositories.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: SettingsRepositoryImpl,
    private val userRepository: UserRepositoryImpl,
) : ViewModel() {

    var viewState by mutableStateOf(SplashViewState())
        private set

    var viewEffect = MutableSharedFlow<SplashViewEffect>()
        private set

    init {
        if (BuildConfig.BUILD_TYPE == "debug")
            viewModelScope.launch {
                if (!userRepository.isUser("1", "1"))
                    userRepository.saveUser(
                        fullName = "Test Test",
                        numberPhone = "8913",
                        userName = "Test ",
                        balance = 50.0,
                        email = "1",
                        password = "1",
                    )
            }
    }

    fun onEvent(event: SplashEvent) {
        when (event) {
            SplashEvent.StartScreen -> startScreen()
        }
    }

    private fun startScreen() {
        viewModelScope.launch {
            viewState = viewState.copy(isLoading = true)
            val authRegistryStatus = authRepository.getAuthStatus()
            viewState = viewState.copy(isLoading = false)
            if (authRegistryStatus)
                viewEffect.emit(SplashViewEffect.MoveMainContentScreen)
            else
                viewEffect.emit(SplashViewEffect.MoveSignInScreen)
        }
    }
}

