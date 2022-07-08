package com.fiz.wisecrypto.ui.screens.main.profile.notifications

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

@HiltViewModel
class ProfileNotificationsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepositoryImpl,
    private val userRepository: UserRepositoryImpl,

    ) : ViewModel() {
    var viewState by mutableStateOf(ProfileNotificationsViewState())
        private set

    var viewEffect = MutableSharedFlow<ProfileNotificationsViewEffect>()
        private set

    init {
        viewModelScope.launch {
            viewState = viewState.copy(
                portfolioSwitchValue = settingsRepository.getIsPortfolioNotification(),
                popularSwitchValue = settingsRepository.getIsPopularNotification(),
                watchlistSwitchValue = settingsRepository.getIsWatchlistNotification(),
                promotionsSwitchValue = settingsRepository.getIsPromotionsNotification(),
            )
        }
    }

    fun onEvent(event: ProfileNotificationsEvent) {
        when (event) {
            ProfileNotificationsEvent.BackButtonClicked -> backButtonClicked()
            ProfileNotificationsEvent.PopularSwitchClicked -> popularSwitchClicked()
            ProfileNotificationsEvent.PortfolioSwitchClicked -> portfolioSwitchClicked()
            ProfileNotificationsEvent.PromotionsSwitchClicked -> promotionsSwitchClicked()
            ProfileNotificationsEvent.SaveButtonClicked -> saveButtonClicked()
            ProfileNotificationsEvent.WatchlistSwitchClicked -> watchlistSwitchClicked()
        }
    }

    private fun watchlistSwitchClicked() {
        viewModelScope.launch {
            viewState = viewState.copy(watchlistSwitchValue = !viewState.watchlistSwitchValue)
        }
    }

    private fun promotionsSwitchClicked() {
        viewModelScope.launch {
            viewState = viewState.copy(promotionsSwitchValue = !viewState.promotionsSwitchValue)
        }
    }

    private fun portfolioSwitchClicked() {
        viewModelScope.launch {
            viewState = viewState.copy(portfolioSwitchValue = !viewState.portfolioSwitchValue)
        }
    }

    private fun popularSwitchClicked() {
        viewModelScope.launch {
            viewState = viewState.copy(popularSwitchValue = !viewState.popularSwitchValue)
        }
    }

    private fun saveButtonClicked() {
        viewModelScope.launch {
            settingsRepository.setIsPortfolioNotification(viewState.portfolioSwitchValue)
            settingsRepository.setIsPopularNotification(viewState.popularSwitchValue)
            settingsRepository.setIsWatchlistNotification(viewState.watchlistSwitchValue)
            settingsRepository.setIsPromotionsNotification(viewState.promotionsSwitchValue)
            viewEffect.emit(ProfileNotificationsViewEffect.MoveReturn)
        }
    }

    private fun backButtonClicked() {
        viewModelScope.launch {
            viewEffect.emit(ProfileNotificationsViewEffect.MoveReturn)
        }
    }
}