package com.fiz.wisecrypto.ui.screens.main.market.buy

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.repositories.CoinRepositoryImpl
import com.fiz.wisecrypto.data.repositories.UserRepositoryImpl
import com.fiz.wisecrypto.domain.use_case.CurrentUserUseCase
import com.fiz.wisecrypto.domain.use_case.FormatUseCase
import com.fiz.wisecrypto.ui.util.BaseViewModel
import com.fiz.wisecrypto.ui.util.ERROR_SELL
import com.fiz.wisecrypto.ui.util.ERROR_TEXT_FIELD
import com.fiz.wisecrypto.ui.util.toDouble2
import com.fiz.wisecrypto.util.Resource
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
    var email: String? = null

    init {
        viewModelScope.launch {
            currentUserUseCase.getCurrentUser()
                .collectLatest { user ->
                    user ?: return@collectLatest
                    email = user.email
                    viewState = viewState.copy(
                        valueBalance = formatUseCase.getFormatBalance(user.balance),
                    )
                    refresh()
                }
        }
    }

    fun onEvent(event: MarketBuyEvent) {
        when (event) {
            MarketBuyEvent.OnRefresh -> onRefresh()
            MarketBuyEvent.Started -> started()
            MarketBuyEvent.Stopped -> stopped()
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
                val currency = value.substringAfter("$")
                viewState = viewState.copy(currencyForBuy = currency)
                refresh()

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
        viewModelScope.launch {

            try {
                val currency = viewState.currencyForBuy
                if (currency.toDouble2() > viewState.valueBalance.toDouble2())
                    throw Exception("No money")

                if (userRepository.buyActive(
                        email ?: return@launch,
                        idCoin ?: return@launch,
                        currency.toDouble2(),
                        viewState.valueCoin.toDouble2()
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
        }
    }

    private fun addBalanceClicked() {
        viewModelScope.launch {
            viewEffect.emit(MarketBuyViewEffect.AddBalanceClicked)
        }
    }

    override suspend fun refresh() {
        idCoin?.let {
            val currency = try {
                viewState.currencyForBuy.toDouble2()
            } catch (e: Exception) {
                return
            }
            viewState = viewState.copy(isLoading = true)
            val result = coinRepository.getCoin(it)
            if (result is Resource.Success) {
                val coin = result.data ?: return
                viewState = viewState.copy(
                    icon = coin.image,
                    valueCoin = formatUseCase.getFormatCoin(currency / coin.currentPrice),
                    nameCoin = coin.name,
                    symbolCoin = coin.symbol.uppercase()
                )
            } else {
                viewEffect.emit(
                    MarketBuyViewEffect.ShowError(
                        result.message
                    )
                )
            }
            viewState = viewState.copy(isLoading = false)
        }
    }

}