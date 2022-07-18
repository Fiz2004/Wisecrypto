package com.fiz.wisecrypto.ui.screens.main.profile.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.repositories.CoinRepositoryImpl
import com.fiz.wisecrypto.data.repositories.SettingsRepositoryImpl
import com.fiz.wisecrypto.domain.use_case.CurrentUserUseCase
import com.fiz.wisecrypto.domain.use_case.FormatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val formatUseCase: FormatUseCase,
    private val coinRepository: CoinRepositoryImpl,
    private val currentUserUseCase: CurrentUserUseCase,
    private val authRepository: SettingsRepositoryImpl
) : ViewModel() {
    var viewState by mutableStateOf(ProfileViewState())
        private set

    var viewEffect = MutableSharedFlow<ProfileViewEffect>()
        private set

    init {
        viewModelScope.launch {
            currentUserUseCase.getCurrentUser()
                .collectLatest { user ->
                    user ?: return@collectLatest

                    val balance = user.balance / coinRepository.getCoefCurrentToUsd()
                    val formatBalance = formatUseCase.getFormatBalance(balance)

                    val balanceUsd = user.balance
                    val formatBalanceUsd = formatUseCase.getFormatBalanceUsd(balanceUsd)
                    viewState = viewState.copy(
                        fullName = user.fullName,
                        balanceCurrentCurrency = formatBalance,
                        balanceUsd = formatBalanceUsd
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
            ProfileEvent.CancelExitClicked -> cancelExitClicked()
            ProfileEvent.ConfirmExitClicked -> confirmExitClicked()
        }
    }

    private fun confirmExitClicked() {
        viewModelScope.launch {
            authRepository.exit()
            viewEffect.emit(ProfileViewEffect.MoveSignInScreen)
        }
    }

    private fun cancelExitClicked() {
        viewModelScope.launch {
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
            viewEffect.emit(ProfileViewEffect.ShowAlertDialogConfirmExit)
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