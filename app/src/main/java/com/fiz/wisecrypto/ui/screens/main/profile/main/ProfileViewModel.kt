package com.fiz.wisecrypto.ui.screens.main.profile.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.repositories.SettingsRepositoryImpl
import com.fiz.wisecrypto.data.repositories.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val coefCurrentToUsd = 1 / 52.0

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl,
    private val authRepository: SettingsRepositoryImpl
) : ViewModel() {
    var viewState by mutableStateOf(ProfileViewState())
        private set

    var viewEffect = MutableSharedFlow<ProfileViewEffect>()
        private set

    init {
        viewModelScope.launch {
            val email = authRepository.getAuthEmail()
            if (email == null) {
                viewEffect.emit(ProfileViewEffect.MoveSignInScreen)
            } else {
                val user = userRepository.getUserInfo(email)
                if (user == null)
                    viewEffect.emit(ProfileViewEffect.MoveSignInScreen)
                else
                    viewState = viewState.copy(
                        fullName = user.fullName,
                        balanceCurrentCurrency = "%.2f".format(user.balance),
                        balanceUsd = "%.0f".format(user.balance * coefCurrentToUsd)
                    )
            }
        }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.AddClicked -> addClicked()
            ProfileEvent.ListTransactionsClicked -> listTransactionsClicked()
            ProfileEvent.NotificationsClicked -> notificationsClicked()
            ProfileEvent.PaymentClicked -> paymentClicked()
            ProfileEvent.PrivacyClicked -> privacyClicked()
            ProfileEvent.ProfileExitClicked -> profileExitClicked()
            ProfileEvent.PullClicked -> pullClicked()
            ProfileEvent.ChangeAvatarClicked -> changeAvatarClicked()
        }
    }

    private fun changeAvatarClicked() {
        viewModelScope.launch {
            viewEffect.emit(ProfileViewEffect.ShowChangeAvatarScreen)
        }
    }

    private fun pullClicked() {
        viewModelScope.launch {
            viewEffect.emit(ProfileViewEffect.MovePullScreen)
        }
    }

    private fun profileExitClicked() {
        viewModelScope.launch {
            authRepository.exit()
            viewEffect.emit(ProfileViewEffect.MoveSignInScreen)
        }
    }

    private fun privacyClicked() {
        viewModelScope.launch {
            viewEffect.emit(ProfileViewEffect.MovePrivacyScreen)
        }
    }

    private fun paymentClicked() {
        viewModelScope.launch {
            viewEffect.emit(ProfileViewEffect.MovePaymentScreen)
        }
    }

    private fun notificationsClicked() {
        viewModelScope.launch {
            viewEffect.emit(ProfileViewEffect.MoveNotificationsScreen)
        }
    }

    private fun listTransactionsClicked() {
        viewModelScope.launch {
            viewEffect.emit(ProfileViewEffect.MoveListTransactionsScreen)
        }
    }

    private fun addClicked() {
        viewModelScope.launch {
            viewEffect.emit(ProfileViewEffect.MoveAddScreen)
        }
    }

}