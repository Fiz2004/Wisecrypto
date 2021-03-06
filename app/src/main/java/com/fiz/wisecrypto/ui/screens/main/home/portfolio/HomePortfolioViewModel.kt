package com.fiz.wisecrypto.ui.screens.main.home.portfolio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.repositories.CoinRepositoryImpl
import com.fiz.wisecrypto.domain.models.Coin
import com.fiz.wisecrypto.domain.models.User
import com.fiz.wisecrypto.domain.use_case.CurrentUserUseCase
import com.fiz.wisecrypto.domain.use_case.PortfolioUseCase
import com.fiz.wisecrypto.ui.util.BaseViewModel
import com.fiz.wisecrypto.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePortfolioViewModel @Inject constructor(
    private val currentUserUseCase: CurrentUserUseCase,
    private val portfolioUseCase: PortfolioUseCase,
    private val coinRepository: CoinRepositoryImpl,

    ) : BaseViewModel() {
    var viewState by mutableStateOf(HomePortfolioViewState())
        private set

    var viewEffect = MutableSharedFlow<HomePortfolioViewEffect>()
        private set

    var user: User? = null
    var coins: List<Coin>? = null

    init {
        viewModelScope.launch {
            currentUserUseCase.getCurrentUser()
                .collectLatest { user ->
                    this@HomePortfolioViewModel.user = user
                    refresh()
                }
        }
    }

    fun onEvent(event: HomePortfolioEvent) {
        when (event) {
            HomePortfolioEvent.BackButtonClicked -> backButtonClicked()
            is HomePortfolioEvent.YourActiveClicked -> yourActiveClicked(event.id)
        }
    }

    private fun backButtonClicked() {
        viewModelScope.launch {
            viewEffect.emit(HomePortfolioViewEffect.MoveReturn)
        }
    }

    private fun yourActiveClicked(id: String) {
        viewModelScope.launch {
            viewEffect.emit(HomePortfolioViewEffect.MoveHomeDetailScreen(id))
        }
    }

    override suspend fun request() {
        viewState = viewState.copy(isLoading = true)

        when (val result = coinRepository.getCoins()) {
            is Resource.Success -> {
                coins = result.data ?: listOf()
                refresh()
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


    private fun refresh() {
        user?.let { user ->
            coins?.let { coins ->
                val portfolioUi = portfolioUseCase.getPortfolioUi(user.actives, coins)

                viewState = viewState.copy(
                    portfolio = portfolioUi.actives,
                    balancePortfolio = portfolioUi.balancePortfolio,
                    isPricePortfolioIncreased = portfolioUi.isPricePortfolioIncreased,
                    percentageChangedBalance = portfolioUi.percentageChangedBalance,
                    totalReturn = portfolioUi.totalReturn,
                )
            }
        }
    }
}