package com.fiz.wisecrypto.ui.screens.main.home.portfolio

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
import com.fiz.wisecrypto.util.Consts
import com.fiz.wisecrypto.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class HomePortfolioViewModel @Inject constructor(
    private val portfolioUseCase: PortfolioUseCase,
    private val authRepository: SettingsRepositoryImpl,
    private val userRepository: UserRepositoryImpl,
    private val coinRepository: CoinRepositoryImpl,

    ) : ViewModel() {
    var viewState by mutableStateOf(HomePortfolioViewState())
        private set

    var viewEffect = MutableSharedFlow<HomePortfolioViewEffect>()
        private set

    private var jobRefresh: Job? = null

    var user: User? = null

    init {
        viewModelScope.launch {
            val email = authRepository.getAuthEmail()
            if (email != null) {
                user = userRepository.getUserInfo(email)
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
        val result = coinRepository.getCoins()
        if (result is Resource.Success) {
            val pricePortfolio = portfolioUseCase.getPricePortfolio(
                user?.portfolio ?: listOf(),
                result.data ?: listOf()
            )
            val pricePortfolioForBuy =
                portfolioUseCase.getPricePortfolioForBuy(user?.portfolio ?: listOf())
            val changePercentageBalance =
                portfolioUseCase.getChangePercentageBalance(
                    user?.portfolio ?: listOf(),
                    result.data ?: listOf()
                )
            val totalReturn = pricePortfolio - pricePortfolioForBuy
            viewState = viewState.copy(
                portfolio = user?.portfolio?.map { it.toActiveUi(result.data) } ?: listOf(),
                pricePortfolio = "\$${"%.2f".format(pricePortfolio)}",
                changePercentageBalance = changePercentageBalance,
                totalReturn = "\$${"%.2f".format(totalReturn)}",
            )
        } else
            viewEffect.emit(
                HomePortfolioViewEffect.ShowError(
                    result.message ?: "Ошибка при загрузке данных из сети"
                )
            )
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