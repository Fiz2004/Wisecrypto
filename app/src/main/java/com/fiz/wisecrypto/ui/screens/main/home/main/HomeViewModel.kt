package com.fiz.wisecrypto.ui.screens.main.home.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.data_source.coinsStore
import com.fiz.wisecrypto.data.repositories.AuthRepositoryImpl
import com.fiz.wisecrypto.data.repositories.UserRepositoryImpl
import com.fiz.wisecrypto.domain.models.Coin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeViewState(
    val fullName: String = "",
    val photo: String = "",
    val coins: List<Coin> = coinsStore
)

sealed class HomeViewEffect {
    object MoveSignIn : HomeViewEffect()
    object MoveNotificationScreen : HomeViewEffect()
}

sealed class HomeEvent {
    object NotificationClicked : HomeEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl,
    private val userRepository: UserRepositoryImpl,

    ) : ViewModel() {
    var viewState by mutableStateOf(HomeViewState())
        private set

    var viewEffect = MutableSharedFlow<HomeViewEffect>()
        private set

    init {
        viewModelScope.launch {
            val email = authRepository.getAuthEmail()
            if (email == null) {
                viewEffect.emit(HomeViewEffect.MoveSignIn)
            } else {
                val user = userRepository.getUserInfo(email)
                if (user == null)
                    viewEffect.emit(HomeViewEffect.MoveSignIn)
                else
                    viewState = viewState.copy(fullName = user.fullName)
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.NotificationClicked -> notificationClicked()
        }
    }

    private fun notificationClicked() {
        viewModelScope.launch {
            viewEffect.emit(HomeViewEffect.MoveNotificationScreen)
        }
    }
}