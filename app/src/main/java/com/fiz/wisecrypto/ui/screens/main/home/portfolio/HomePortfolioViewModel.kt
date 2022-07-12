package com.fiz.wisecrypto.ui.screens.main.home.portfolio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.repositories.CoinRepositoryImpl
import com.fiz.wisecrypto.domain.models.Active
import com.fiz.wisecrypto.domain.use_case.CurrentUserUseCase
import com.fiz.wisecrypto.domain.use_case.PortfolioUseCase
import com.fiz.wisecrypto.util.Consts
import com.fiz.wisecrypto.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class HomePortfolioViewModel @Inject constructor(
    private val currentUserUseCase: CurrentUserUseCase,
    private val portfolioUseCase: PortfolioUseCase,
    private val coinRepository: CoinRepositoryImpl,

    ) : ViewModel() {
    var viewState by mutableStateOf(HomePortfolioViewState())
        private set

    var viewEffect = MutableSharedFlow<HomePortfolioViewEffect>()
        private set

    private var jobRefresh: Job? = null

    var actives: List<Active> = listOf()

    init {
        viewModelScope.launch {
            currentUserUseCase.getCurrentUser()
                .collectLatest { user ->
                    user ?: return@collectLatest

                    actives = user.actives
                }
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
                    delay(Consts.TIME_REFRESH_NETWORK_MS.toLong())
                }
            }
    }

    private suspend fun refreshState() {
        viewState = viewState.copy(isLoading = true)

        when (val result = coinRepository.getCoins()) {
            is Resource.Success -> {
                val coins = result.data ?: listOf()
                val portfolioUi = portfolioUseCase.getPortfolioUi(actives, coins)

                viewState = viewState.copy(
                    portfolio = portfolioUi.actives,
                    pricePortfolio = portfolioUi.pricePortfolio,
                    pricePortfolioIncreased = portfolioUi.pricePortfolioIncreased,
                    changePercentagePricePortfolio = portfolioUi.changePercentagePricePortfolio,
                    totalReturn = portfolioUi.totalReturn,
                )
            }
            is Resource.Error -> {
                viewEffect.emit(
                    HomePortfolioViewEffect.ShowError(
                        result.message
                    )
                )
            }
        }

        viewState = viewState.copy(isLoading = false)
    }

    fun onEvent(event: HomePortfolioEvent) {
        when (event) {
            HomePortfolioEvent.BackButtonClicked -> backButtonClicked()
            HomePortfolioEvent.Started -> started()
            HomePortfolioEvent.Stopped -> stopped()
        }
    }

    private fun backButtonClicked() {
        viewModelScope.launch {
            viewEffect.emit(HomePortfolioViewEffect.MoveReturn)
        }
    }
}