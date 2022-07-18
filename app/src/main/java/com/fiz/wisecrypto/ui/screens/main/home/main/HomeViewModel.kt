package com.fiz.wisecrypto.ui.screens.main.home.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.repositories.CoinRepositoryImpl
import com.fiz.wisecrypto.domain.models.Active
import com.fiz.wisecrypto.domain.use_case.CurrentUserUseCase
import com.fiz.wisecrypto.domain.use_case.FormatUseCase
import com.fiz.wisecrypto.domain.use_case.PortfolioUseCase
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

    ) : BaseViewModel(){
    var viewState by mutableStateOf(HomeViewState(isLoading = true))
        private set

    var viewEffect = MutableSharedFlow<HomeViewEffect>()
        private set

    var actives: List<Active> = listOf()
    var watchlist: List<String> = listOf()

    init {
        viewModelScope.launch {
            currentUserUseCase.getCurrentUser()
                .collectLatest { user ->
                    user ?: return@collectLatest

                    val fullName = user.fullName
                    val balanceUsd = user.balance
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
            HomeEvent.OnRefresh -> onRefresh()
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

    private fun notificationClicked() {
        viewModelScope.launch {
            viewEffect.emit(HomeViewEffect.MoveNotificationScreen)
        }
    }

    override suspend fun refresh() {
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
}


