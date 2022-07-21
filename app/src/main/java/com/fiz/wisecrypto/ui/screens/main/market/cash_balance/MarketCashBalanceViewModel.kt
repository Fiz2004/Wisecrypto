package com.fiz.wisecrypto.ui.screens.main.market.cash_balance

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.repositories.UserRepositoryImpl
import com.fiz.wisecrypto.domain.use_case.CurrentUserUseCase
import com.fiz.wisecrypto.domain.use_case.FormatUseCase
import com.fiz.wisecrypto.ui.util.BaseViewModel
import com.fiz.wisecrypto.util.Consts.COMMISSION
import com.fiz.wisecrypto.util.ERROR_SELL
import com.fiz.wisecrypto.util.ERROR_TEXT_FIELD
import com.fiz.wisecrypto.util.toDoubleOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MarketCashBalanceViewModel @Inject constructor(
    private val currentUserUseCase: CurrentUserUseCase,
    private val formatUseCase: FormatUseCase,
    private val userRepository: UserRepositoryImpl
) : BaseViewModel() {
    var viewState by mutableStateOf(MarketCashBalanceViewState())
        private set

    var viewEffect = MutableSharedFlow<MarketCashBalanceViewEffect>()
        private set

    var email: String? = null

    init {
        viewModelScope.launch {
            currentUserUseCase.getCurrentUser()
                .collectLatest { user ->
                    user ?: return@collectLatest
                    email = user.email
                    viewState = viewState.copy(
                        valueBalance = formatUseCase.getFormatBalance(user.balanceUi),
                        currencyForBuy = formatUseCase.getFormatBalance(10.0)
                    )
                    request()
                }
        }
    }

    fun onEvent(event: MarketCashBalanceEvent) {
        when (event) {
            MarketCashBalanceEvent.OnRefresh -> onRefresh()
            MarketCashBalanceEvent.Started -> started()
            MarketCashBalanceEvent.Stopped -> stopped()
            MarketCashBalanceEvent.BackButtonClicked -> backButtonClicked()
            MarketCashBalanceEvent.CashButtonClicked -> cashButtonClicked()
            is MarketCashBalanceEvent.ValueCurrencyChanged -> valueCurrencyChanged(event.value)
            MarketCashBalanceEvent.PaymentClicked -> paymentClicked()
            MarketCashBalanceEvent.CashAll -> cashAll()
        }
    }

    private fun cashAll() {
        viewModelScope.launch {
            viewState = viewState.copy(currencyForBuy = viewState.valueBalance)
            request()
        }
    }

    private fun paymentClicked() {

    }

    private fun backButtonClicked() {
        viewModelScope.launch {
            viewEffect.emit(MarketCashBalanceViewEffect.MoveReturn)
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
                    MarketCashBalanceViewEffect.ShowError(
                        ERROR_TEXT_FIELD
                    )
                )
            }
        }
    }

    private fun cashButtonClicked() {
        viewModelScope.launch {

            try {
                val currency =
                    viewState.currencyForBuy.toDoubleOrNull() ?: throw Exception("value no correct")

                val valueBalance =
                    viewState.valueBalance.toDoubleOrNull() ?: throw Exception("value no correct")

                if (currency > valueBalance)
                    throw Exception("value no correct")

                val commission = currency * COMMISSION
                val total = currency + commission

                if (userRepository.cashBalance(
                        email = email ?: return@launch,
                        currency = total,
                    )
                )
                    viewEffect.emit(MarketCashBalanceViewEffect.MoveReturn)
                else {
                    viewEffect.emit(
                        MarketCashBalanceViewEffect.ShowError(
                            ERROR_SELL
                        )
                    )
                }

            } catch (e: Exception) {
                viewEffect.emit(
                    MarketCashBalanceViewEffect.ShowError(
                        ERROR_TEXT_FIELD
                    )
                )
            }
        }
    }

    override suspend fun request() {
        email?.let {
            val currency = viewState.currencyForBuy.toDoubleOrNull() ?: return
            val commission = currency * COMMISSION
            val total = currency + commission
            viewState = viewState.copy(isLoading = true)
            viewState = viewState.copy(
                commission = formatUseCase.getFormatBalance(commission),
                total = formatUseCase.getFormatBalance(total),
            )
            viewState = viewState.copy(isLoading = false)
        }
    }

}