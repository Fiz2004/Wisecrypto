package com.fiz.wisecrypto.ui.screens.main.home.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.repositories.CoinRepositoryImpl
import com.fiz.wisecrypto.domain.models.Coin
import com.fiz.wisecrypto.domain.models.User
import com.fiz.wisecrypto.domain.use_case.CurrentUserUseCase
import com.fiz.wisecrypto.domain.use_case.FormatUseCase
import com.fiz.wisecrypto.domain.use_case.PortfolioUseCase
import com.fiz.wisecrypto.ui.screens.main.models.toCoinUi
import com.fiz.wisecrypto.ui.util.BaseViewModel
import com.fiz.wisecrypto.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val formatUseCase: FormatUseCase,
    private val currentUserUseCase: CurrentUserUseCase,
    private val portfolioUseCase: PortfolioUseCase,
    private val coinRepository: CoinRepositoryImpl,
) : BaseViewModel() {
    var viewState by mutableStateOf(HomeViewState())
        private set

    var viewEffect = MutableSharedFlow<HomeViewEffect>()
        private set

    private var user: User? = null
    private var coins: List<Coin>? = null

    init {
        viewModelScope.launch {
            currentUserUseCase.getCurrentUser()
                .collectLatest { user ->
                    this@HomeViewModel.user = user
                    refresh()
                }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.NotificationClicked -> notificationClicked()
            HomeEvent.YourActiveAllClicked -> yourActiveAllClicked()
            HomeEvent.AddBalanceClicked -> addBalanceClicked()
            is HomeEvent.YourActiveClicked -> yourActiveClicked(event.id)
        }
    }

    private fun notificationClicked() {
        viewModelScope.launch {
            viewEffect.emit(HomeViewEffect.MoveNotificationScreen)
        }
    }

    private fun yourActiveAllClicked() {
        viewModelScope.launch {
            viewEffect.emit(HomeViewEffect.MoveHomePortfolioScreen)
        }
    }

    private fun addBalanceClicked() {
        viewModelScope.launch {
            viewEffect.emit(HomeViewEffect.MoveMarketAddBalance)
        }
    }

    private fun yourActiveClicked(id: String) {
        viewModelScope.launch {
            viewEffect.emit(HomeViewEffect.MoveHomeDetailScreen(id))
        }
    }

    override suspend fun request() {
        viewState = viewState.copy(isLoading = true)

        when (val result = coinRepository.getCoins()) {
            is Resource.Success -> {
                coins = result.data ?: emptyList()
                refresh()
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

    private fun refresh() {
        user?.let { user ->
            coins?.let { coins ->
                val fullName = user.fullName
                val balanceUsd = user.balanceUi
                val formatBalanceUsd = formatUseCase.getFormatBalanceUsd(balanceUsd)
                val portfolioUI =
                    portfolioUseCase.getPortfolioUi(user.actives, coins)
                val coinsWatchList =
                    currentUserUseCase.getCoinsWatchList(user.watchList, coins)

                viewState = viewState.copy(
                    isFirstLoading = false,
                    fullName = fullName,
                    balanceCurrency = formatBalanceUsd,
                    actives = portfolioUI.actives,
                    balancePortfolio = portfolioUI.balancePortfolio,
                    isPricePortfolioIncreased = portfolioUI.isPricePortfolioIncreased,
                    percentageChangedBalance = portfolioUI.percentageChangedBalance,
                    watchlist = coinsWatchList.map { it.toCoinUi() }
                )
            }
        }
    }
}


