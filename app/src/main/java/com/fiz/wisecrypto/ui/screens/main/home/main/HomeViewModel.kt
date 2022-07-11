package com.fiz.wisecrypto.ui.screens.main.home.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.repositories.CoinRepositoryImpl
import com.fiz.wisecrypto.data.repositories.SettingsRepositoryImpl
import com.fiz.wisecrypto.data.repositories.UserRepositoryImpl
import com.fiz.wisecrypto.domain.models.User
import com.fiz.wisecrypto.domain.use_case.PortfolioUseCase
import com.fiz.wisecrypto.ui.screens.main.profile.main.coefCurrentToUsd
import com.fiz.wisecrypto.util.Consts.TIME_REFRESH_NETWORK_MS
import com.fiz.wisecrypto.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val portfolioUseCase: PortfolioUseCase,
    private val authRepository: SettingsRepositoryImpl,
    private val userRepository: UserRepositoryImpl,
    private val coinRepository: CoinRepositoryImpl,

    ) : ViewModel() {
    var viewState by mutableStateOf(HomeViewState())
        private set

    var viewEffect = MutableSharedFlow<HomeViewEffect>()
        private set

    private var jobRefresh: Job? = null

    var user: User? = null

    init {
        viewModelScope.launch {
            val email = authRepository.getAuthEmail()
            if (email != null) {
                user = userRepository.getUserInfo(email)
                if (user != null)
                    viewState = viewState.copy(
                        fullName = user?.fullName ?: "",
                        balance = "%.0f".format((user?.balance ?: 0.0) * coefCurrentToUsd)
                    )
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.NotificationClicked -> notificationClicked()
            HomeEvent.Started -> started()
            HomeEvent.Stopped -> stopped()
        }
    }

    private fun stopped() {
        viewModelScope.launch {
            jobRefresh?.cancelAndJoin()
            jobRefresh = null
        }
    }

    private fun started() {
        if (jobRefresh == null)
            jobRefresh = viewModelScope.launch(Dispatchers.Default) {
                while (isActive) {
                    refreshState()
                    delay(TIME_REFRESH_NETWORK_MS.toLong())
                }
            }
    }

    private suspend fun refreshState() {
        viewState = viewState.copy(isLoading = true)
        val result = coinRepository.getCoins()
        if (result is Resource.Success) {
            val pricePortfolio = portfolioUseCase.getPricePortfolio(
                user?.portfolio ?: listOf(),
                result.data ?: listOf()
            )
            val changePercentageBalance =
                portfolioUseCase.getChangePercentageBalance(
                    user?.portfolio ?: listOf(),
                    result.data ?: listOf()
                )
            viewState = viewState.copy(
                portfolio = user?.portfolio?.map { it.toActiveUi(result.data) } ?: listOf(),
                pricePortfolio = "\$${"%.2f".format(pricePortfolio)}",
                changePercentageBalance = changePercentageBalance,
                coins = result.data?.filter { user?.watchList?.contains(it.id) ?: false }
                    ?: emptyList(),
            )
        } else
            viewEffect.emit(
                HomeViewEffect.ShowError(
                    result.message ?: "Ошибка при загрузке данных из сети"
                )
            )
        viewState = viewState.copy(isLoading = false)
    }

    private fun notificationClicked() {
        viewModelScope.launch {
            viewEffect.emit(HomeViewEffect.MoveNotificationScreen)
        }
    }
}

