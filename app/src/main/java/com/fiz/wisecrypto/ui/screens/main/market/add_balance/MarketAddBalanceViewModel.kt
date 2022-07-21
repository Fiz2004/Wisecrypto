package com.fiz.wisecrypto.ui.screens.main.market.add_balance

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.repositories.UserRepositoryImpl
import com.fiz.wisecrypto.domain.models.User
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
class MarketAddBalanceViewModel @Inject constructor(
    private val currentUserUseCase: CurrentUserUseCase,
    private val formatUseCase: FormatUseCase,
    private val userRepository: UserRepositoryImpl
) : BaseViewModel() {
    var viewState by mutableStateOf(MarketAddBalanceViewState())
        private set

    var viewEffect = MutableSharedFlow<MarketAddBalanceViewEffect>()
        private set

    private var user: User? = null

    init {
        viewModelScope.launch {
            currentUserUseCase.getCurrentUser()
                .collectLatest { user ->
                    this@MarketAddBalanceViewModel.user = user
                    refresh()
                }
        }
    }

    fun onEvent(event: MarketAddBalanceEvent) {
        when (event) {
            MarketAddBalanceEvent.BackButtonClicked -> backButtonClicked()
            MarketAddBalanceEvent.PayButtonClicked -> buyButtonClicked()
            MarketAddBalanceEvent.PaymentClicked -> paymentClicked()
            is MarketAddBalanceEvent.ValueCurrencyChanged -> valueCurrencyChanged(event.value)
        }
    }

    private fun backButtonClicked() {
        viewModelScope.launch {
            viewEffect.emit(MarketAddBalanceViewEffect.MoveReturn)
        }
    }

    private fun buyButtonClicked() {
        user?.let { user ->
            viewModelScope.launch {
                try {
                    val currency =
                        viewState.currencyForBuy.toDoubleOrNull()
                            ?: throw Exception("value no correct")

                    val commission = currency * COMMISSION
                    val total = currency + commission

                    if (userRepository.addBalance(
                            user = user,
                            currency = currency,
                            comission = commission
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
    }

    private fun paymentClicked() {

    }

    private fun valueCurrencyChanged(value: String) {
        viewModelScope.launch {
            try {
                val currency = value.substringAfter("$").trim()
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

    override suspend fun request() {
        refresh()
    }

    private fun refresh() {
        user?.let { user ->
            viewState = viewState.copy(isLoading = true)
            val currency = viewState.currencyForBuy.toDoubleOrNull() ?: return
            val commission = currency * COMMISSION
            val total = currency + commission
            viewState = viewState.copy(
                valueBalance = formatUseCase.getFormatBalance(user.balanceUi),
                commission = formatUseCase.getFormatBalance(commission),
                total = formatUseCase.getFormatBalance(total),
            )
            viewState = viewState.copy(isLoading = false)
        }
    }

}