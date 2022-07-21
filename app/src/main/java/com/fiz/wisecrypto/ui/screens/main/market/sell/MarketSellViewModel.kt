package com.fiz.wisecrypto.ui.screens.main.market.sell

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.repositories.CoinRepositoryImpl
import com.fiz.wisecrypto.data.repositories.UserRepositoryImpl
import com.fiz.wisecrypto.domain.models.CoinFull
import com.fiz.wisecrypto.domain.models.User
import com.fiz.wisecrypto.domain.use_case.CurrentUserUseCase
import com.fiz.wisecrypto.domain.use_case.FormatUseCase
import com.fiz.wisecrypto.ui.util.BaseViewModel
import com.fiz.wisecrypto.util.ERROR_SELL
import com.fiz.wisecrypto.util.ERROR_TEXT_FIELD
import com.fiz.wisecrypto.util.Resource
import com.fiz.wisecrypto.util.toDoubleOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketSellViewModel @Inject constructor(
    private val currentUserUseCase: CurrentUserUseCase,
    private val formatUseCase: FormatUseCase,
    private val userRepository: UserRepositoryImpl,
    private val coinRepository: CoinRepositoryImpl
) : BaseViewModel() {
    var viewState by mutableStateOf(MarketSellViewState())
        private set

    var viewEffect = MutableSharedFlow<MarketSellViewEffect>()
        private set

    var idCoin: String? = null
    private var user: User? = null
    private var coinFull: CoinFull? = null

    init {
        viewModelScope.launch {
            currentUserUseCase.getCurrentUser()
                .collectLatest { user ->
                    this@MarketSellViewModel.user = user
                    val active = user?.actives?.find { it.id == idCoin } ?: return@collectLatest
                    val initCoinForSell = formatUseCase.getFormatCoin(active.countUi)
                    viewState = viewState.copy(
                        userCoinForSell = initCoinForSell,
                    )
                    refresh()
                }
        }
    }

    fun onEvent(event: MarketSellEvent) {
        when (event) {
            MarketSellEvent.BackButtonClicked -> backButtonClicked()
            MarketSellEvent.SellAllButtonClicked -> sellAllButtonClicked()
            MarketSellEvent.SellButtonClicked -> sellButtonClicked()
            is MarketSellEvent.ValueCoinChanged -> valueCoinChanged(event.value)
        }
    }

    private fun backButtonClicked() {
        viewModelScope.launch {
            viewEffect.emit(MarketSellViewEffect.MoveReturn)
        }
    }

    private fun valueCoinChanged(value: String) {
        viewModelScope.launch {
            try {
                val coin = value.split(" ")[0]
                viewState = viewState.copy(userCoinForSell = coin)
                request()

            } catch (e: Exception) {
                viewEffect.emit(
                    MarketSellViewEffect.ShowError(
                        ERROR_TEXT_FIELD
                    )
                )
            }
        }
    }

    private fun sellButtonClicked() {
        user?.let { user ->
            viewModelScope.launch {
                viewState = viewState.copy(isLoading = true)
                try {
                    val userCoinForSell =
                        viewState.userCoinForSell.toDoubleOrNull() ?: return@launch
                    val valueActiveCoin =
                        viewState.valueActiveCoin.toDoubleOrNull() ?: return@launch
                    val priceCurrency = viewState.priceCurrency.toDoubleOrNull() ?: return@launch
                    if (userCoinForSell > valueActiveCoin)
                        throw Exception("No money")

                    if (userRepository.sellActive(
                            user = user,
                            idCoin = idCoin ?: return@launch,
                            userCoinForSell = userCoinForSell,
                            priceCurrency = priceCurrency
                        )
                    )
                        viewEffect.emit(MarketSellViewEffect.MoveReturn)
                    else {
                        viewEffect.emit(
                            MarketSellViewEffect.ShowError(
                                ERROR_SELL
                            )
                        )
                    }

                } catch (e: Exception) {
                    viewEffect.emit(
                        MarketSellViewEffect.ShowError(
                            ERROR_TEXT_FIELD
                        )
                    )
                }
                viewState = viewState.copy(isLoading = false)
            }
        }
    }

    private fun sellAllButtonClicked() {
        viewModelScope.launch {
            viewState = viewState.copy(userCoinForSell = viewState.valueActiveCoin)
            request()
        }
    }

    override suspend fun request() {
        idCoin?.let {
            viewState = viewState.copy(isLoading = true)
            val result = coinRepository.getCoin(it)
            if (result is Resource.Success) {
                coinFull = result.data ?: CoinFull()
                refresh()
            } else {
                viewEffect.emit(
                    MarketSellViewEffect.ShowError(
                        result.message
                    )
                )
            }
            viewState = viewState.copy(isLoading = false)
        }
    }

    private fun refresh() {
        user?.let { user ->
            coinFull?.let { coinFull ->
                val active = user.actives.find { it.id == idCoin } ?: return
                val currency = viewState.userCoinForSell.toDoubleOrNull() ?: return

                viewState = viewState.copy(
                    valueActiveCoin = formatUseCase.getFormatCoin(active.countUi),
                    icon = coinFull.image,
                    priceCurrency = formatUseCase.getFormatBalance(
                        coinFull.currentPrice * currency
                    ),
                    nameCoin = coinFull.name,
                    symbolCoin = coinFull.symbol.uppercase()
                )
            }
        }
    }

}