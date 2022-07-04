package com.fiz.wisecrypto.ui.screens.main.home.notification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.repositories.AuthRepositoryImpl
import com.fiz.wisecrypto.data.repositories.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeNotificationViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl,
    private val userRepository: UserRepositoryImpl,

    ) : ViewModel() {
    var viewState by mutableStateOf(HomeNotificationViewState())
        private set

    var viewEffect = MutableSharedFlow<HomeNotificationViewEffect>()
        private set

    fun onEvent(event: HomeNotificationEvent) {
        when (event) {
            HomeNotificationEvent.BackButtonClicked -> backButtonClicked()
        }
    }

    private fun backButtonClicked() {
        viewModelScope.launch {
            viewEffect.emit(HomeNotificationViewEffect.MoveReturn)
        }
    }
}