package com.fiz.wisecrypto.ui.screens.main.home.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.repositories.CoinRepositoryImpl
import com.fiz.wisecrypto.data.repositories.SettingsRepositoryImpl
import com.fiz.wisecrypto.data.repositories.UserRepositoryImpl
import com.fiz.wisecrypto.util.Consts.TIME_REFRESH_NETWORK_MS
import com.fiz.wisecrypto.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: SettingsRepositoryImpl,
    private val userRepository: UserRepositoryImpl,
    private val coinRepository: CoinRepositoryImpl,

    ) : ViewModel() {
    var viewState by mutableStateOf(HomeViewState())
        private set

    var viewEffect = MutableSharedFlow<HomeViewEffect>()
        private set

    private var jobRefresh: Job? = null

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
            val balance = userRepository.portfolio.fold(0.0) { acc, active ->
                acc + active.count * (result.data?.first { it.id == active.id }?.currentPrice
                    ?: 0.0)
            }
            val balanceForBuy = userRepository.portfolio.fold(0.0) { acc, active ->
                acc + active.count * active.countForBuy
            }
            val divided = balance / balanceForBuy * 100
            val percent = if (divided > 0) divided - 100 else 100 - divided
            viewState = viewState.copy(
                portfolio = userRepository.portfolio.map { it.toActiveUi(result.data) },
                balance = "\$${"%.2f".format(balance)}",
                changePercentageBalance = percent,
                coins = result.data?.filter { userRepository.watchList.contains(it.id) }
                    ?: emptyList()
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

