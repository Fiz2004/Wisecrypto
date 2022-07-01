package com.fiz.wisecrypto.ui.screens.main.home.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.repositories.AuthRepositoryImpl
import com.fiz.wisecrypto.data.repositories.CoinRepositoryImpl
import com.fiz.wisecrypto.data.repositories.UserRepositoryImpl
import com.fiz.wisecrypto.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl,
    private val userRepository: UserRepositoryImpl,
    private val coinRepository: CoinRepositoryImpl,

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
                viewEffect.emit(HomeViewEffect.ShowError("Сбой загрузки из сети"))
            viewState = viewState.copy(isLoading = false)

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

