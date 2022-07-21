package com.fiz.wisecrypto.ui.screens.main.market.buy

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
class MarketBuyViewModel @Inject constructor(
    private val currentUserUseCase: CurrentUserUseCase,
    private val formatUseCase: FormatUseCase,
    private val userRepository: UserRepositoryImpl,
    private val coinRepository: CoinRepositoryImpl
) : BaseViewModel() {
    var viewState by mutableStateOf(MarketBuyViewState())
        private set

    var viewEffect = MutableSharedFlow<MarketBuyViewEffect>()
        private set

    var idCoin: String? = null
    private var user: User? = null
    private var coinFull: CoinFull? = null

    init {
        viewModelScope.launch {
            currentUserUseCase.getCurrentUser()
                .collectLatest { user ->
                    this@MarketBuyViewModel.user = user
                    val initCurrencyForBuy = formatUseCase.getFormatBalance(
                        user?.balanceUi ?: return@collectLatest
                    )
                    viewState = viewState.copy(
                        currencyForBuy = initCurrencyForBuy,
                    )
                    refresh()
                }
        }
    }

    fun onEvent(event: MarketBuyEvent) {
        when (event) {
            MarketBuyEvent.BackButtonClicked -> backButtonClicked()
            MarketBuyEvent.AddBalanceClicked -> addBalanceClicked()
            MarketBuyEvent.BuyButtonClicked -> buyButtonClicked()
            is MarketBuyEvent.ValueCurrencyChanged -> valueCurrencyChanged(event.value)
        }
    }

    private fun backButtonClicked() {
        viewModelScope.launch {
            viewEffect.emit(MarketBuyViewEffect.MoveReturn)
        }
    }

    private fun valueCurrencyChanged(value: String) {
        viewModelScope.launch {
            try {
                val currency = value.substringAfter("$").trim()
                viewState = viewState.copy(currencyForBuy = currency)
                request()

            } catch (e: Exception) {
                viewEffect.emit(
                    MarketBuyViewEffect.ShowError(
                        ERROR_TEXT_FIELD
                    )
                )
            }
        }
    }

    private fun buyButtonClicked() {
        user?.let { user ->
            viewModelScope.launch {
                viewState = viewState.copy(isLoading = true)
                try {
                    val currency = viewState.currencyForBuy.toDoubleOrNull() ?: return@launch
                    val balance = viewState.valueBalance.toDoubleOrNull() ?: return@launch
                    val valueCoin = viewState.valueCoin.toDoubleOrNull() ?: return@launch
                    if (currency > balance)
                        throw Exception("No money")

                    if (userRepository.buyActive(
                            user,
                            idCoin = idCoin ?: return@launch,
                            currency = currency,
                            valueCoin = valueCoin
                        )
                    )
                        viewEffect.emit(MarketBuyViewEffect.MoveReturn)
                    else {
                        viewEffect.emit(
                            MarketBuyViewEffect.ShowError(
                                ERROR_SELL
                            )
                        )
                    }

                } catch (e: Exception) {
                    viewEffect.emit(
                        MarketBuyViewEffect.ShowError(
                            ERROR_TEXT_FIELD
                        )
                    )
                }
                viewState = viewState.copy(isLoading = false)
            }
        }
    }

    private fun addBalanceClicked() {
        viewModelScope.launch {
            viewEffect.emit(MarketBuyViewEffect.AddBalanceClicked)
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
                    MarketBuyViewEffect.ShowError(result.message)
                )
            }
            viewState = viewState.copy(isLoading = false)
        }
    }

    private fun refresh() {
        user?.let { user ->
            coinFull?.let { coinFull ->
                val currency = viewState.currencyForBuy.toDoubleOrNull() ?: return
                viewState = viewState.copy(
                    valueBalance = formatUseCase.getFormatBalance(user.balanceUi),
                    valueCoin = formatUseCase.getFormatCoin(currency / coinFull.currentPrice),
                    nameCoin = coinFull.name,
                    symbolCoin = coinFull.symbol.uppercase()
                )
            }
        }
    }

}