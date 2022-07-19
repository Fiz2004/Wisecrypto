package com.fiz.wisecrypto.ui.screens.main.market.add_balance

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.repositories.UserRepositoryImpl
import com.fiz.wisecrypto.domain.use_case.CurrentUserUseCase
import com.fiz.wisecrypto.domain.use_case.FormatUseCase
import com.fiz.wisecrypto.ui.util.BaseViewModel
import com.fiz.wisecrypto.util.ERROR_SELL
import com.fiz.wisecrypto.util.ERROR_TEXT_FIELD
import com.fiz.wisecrypto.util.toDoubleOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketAddBalanceViewModel @Inject constructor(
    private val currentUserUseCase: CurrentUserUseCase,
    private val formatUseCase: FormatUseCase,
    private val userRepository: UserRepositoryImpl
) : BaseViewModel() {
    var viewState by mutableStateOf(MarketAddBalanceViewState())
        private set

    var viewEffect = MutableSharedFlow<MarketAddBalanceViewEffect>()
        private set

    var email: String? = null

    init {
        viewModelScope.launch {
            currentUserUseCase.getCurrentUser()
                .collectLatest { user ->
                    user ?: return@collectLatest
                    email = user.email
                    viewState = viewState.copy(
                        valueBalance = formatUseCase.getFormatBalance(user.balance),
                        currencyForBuy = formatUseCase.getFormatBalance(10.0)
                    )
                    refresh()
                }
        }
    }

    fun onEvent(event: MarketAddBalanceEvent) {
        when (event) {
            MarketAddBalanceEvent.OnRefresh -> onRefresh()
            MarketAddBalanceEvent.Started -> started()
            MarketAddBalanceEvent.Stopped -> stopped()
            MarketAddBalanceEvent.BackButtonClicked -> backButtonClicked()
            MarketAddBalanceEvent.PayButtonClicked -> buyButtonClicked()
            is MarketAddBalanceEvent.ValueCurrencyChanged -> valueCurrencyChanged(event.value)
            MarketAddBalanceEvent.PaymentClicked -> {}
        }
    }

    private fun backButtonClicked() {
        viewModelScope.launch {
            viewEffect.emit(MarketAddBalanceViewEffect.MoveReturn)
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
                    MarketAddBalanceViewEffect.ShowError(
                        ERROR_TEXT_FIELD
                    )
                )
            }
        }
    }

    private fun buyButtonClicked() {
        viewModelScope.launch {

            try {
                val currency =
                    viewState.currencyForBuy.toDoubleOrNull() ?: throw Exception("value no correct")

                if (userRepository.addBalance(
                        email = email ?: return@launch,
                        currency = currency,
                    )
                )
                    viewEffect.emit(MarketAddBalanceViewEffect.MoveReturn)
                else {
                    viewEffect.emit(
                        MarketAddBalanceViewEffect.ShowError(
                            ERROR_SELL
                        )
                    )
                }

            } catch (e: Exception) {
                viewEffect.emit(
                    MarketAddBalanceViewEffect.ShowError(
                        ERROR_TEXT_FIELD
                    )
                )
            }
        }
    }

    override suspend fun refresh() {
        email?.let {
            val currency = viewState.currencyForBuy.toDoubleOrNull() ?: return
            val commission = currency / 50.0
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