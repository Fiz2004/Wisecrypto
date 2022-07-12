package com.fiz.wisecrypto.ui.screens.main.home.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.repositories.CoinRepositoryImpl
import com.fiz.wisecrypto.domain.models.Active
import com.fiz.wisecrypto.domain.use_case.CurrentUserUseCase
import com.fiz.wisecrypto.domain.use_case.FormatUseCase
import com.fiz.wisecrypto.domain.use_case.PortfolioUseCase
import com.fiz.wisecrypto.util.Consts.TIME_REFRESH_NETWORK_MS
import com.fiz.wisecrypto.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val formatUseCase: FormatUseCase,
    private val currentUserUseCase: CurrentUserUseCase,
    private val portfolioUseCase: PortfolioUseCase,
    private val coinRepository: CoinRepositoryImpl,

    ) : ViewModel() {
    var viewState by mutableStateOf(HomeViewState())
        private set

    var viewEffect = MutableSharedFlow<HomeViewEffect>()
        private set

    private var jobRefresh: Job? = null

    var actives: List<Active> = listOf()
    var watchlist: List<String> = listOf()

    init {
        viewModelScope.launch {
            currentUserUseCase.getCurrentUser()
                .collectLatest { user ->
                    user ?: return@collectLatest

                    val fullName = user.fullName
                    val balanceUsd = (user.balance) * coinRepository.getCoefCurrentToUsd()
                    val formatBalanceUsd = formatUseCase.getFormatBalanceUsd(balanceUsd)

                    actives = user.actives
                    watchlist = user.watchList

                    viewState = viewState.copy(
                        fullName = fullName,
                        balance = formatBalanceUsd
                    )
                }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.Started -> started()
            HomeEvent.Stopped -> stopped()
            HomeEvent.NotificationClicked -> notificationClicked()
            HomeEvent.YourActiveAllClicked -> yourActiveAllClicked()
            is HomeEvent.YourActiveClicked -> yourActiveClicked(event.id)
        }
    }

    private fun yourActiveClicked(id: String) {
        viewModelScope.launch {
            viewEffect.emit(HomeViewEffect.MoveHomeDetailScreen(id))
        }
    }

    private fun yourActiveAllClicked() {
        viewModelScope.launch {
            viewEffect.emit(HomeViewEffect.MoveHomePortfolioScreen)
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

        when (val result = coinRepository.getCoins()) {
            is Resource.Success -> {
                val coins = result.data ?: listOf()

                val portfolioUI = portfolioUseCase.getPortfolioUi(actives, coins)
                val coinsWatchList = currentUserUseCase.getCoinsWatchList(watchlist, coins)
                viewState = viewState.copy(
                    actives = portfolioUI.actives,
                    pricePortfolio = portfolioUI.pricePortfolio,
                    pricePortfolioIncreased = portfolioUI.pricePortfolioIncreased,
                    changePercentageBalance = portfolioUI.changePercentagePricePortfolio,
                    watchlist = coinsWatchList
                )
            }
            is Resource.Error -> {
                viewEffect.emit(
                    HomeViewEffect.ShowError(
                        result.message
                    )
                )
            }
        }

        viewState = viewState.copy(isLoading = false)
    }

    private fun stopped() {
        viewModelScope.launch {
            jobRefresh?.cancelAndJoin()
            jobRefresh = null
        }
    }

    private fun notificationClicked() {
        viewModelScope.launch {
            viewEffect.emit(HomeViewEffect.MoveNotificationScreen)
        }
    }
}


