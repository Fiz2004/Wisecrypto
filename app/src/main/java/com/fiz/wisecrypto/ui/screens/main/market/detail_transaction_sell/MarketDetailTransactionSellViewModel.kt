package com.fiz.wisecrypto.ui.screens.main.market.detail_transaction_sell

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.repositories.StatusProcessTransaction
import com.fiz.wisecrypto.data.repositories.UserRepositoryImpl
import com.fiz.wisecrypto.domain.models.User
import com.fiz.wisecrypto.domain.models.transaction.StatusTransaction
import com.fiz.wisecrypto.domain.models.transaction.Transaction
import com.fiz.wisecrypto.domain.models.transaction.TypeTransaction
import com.fiz.wisecrypto.domain.use_case.CurrentUserUseCase
import com.fiz.wisecrypto.domain.use_case.FormatUseCase
import com.fiz.wisecrypto.util.Consts
import com.fiz.wisecrypto.util.ERROR_TRANSACTION
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MarketDetailTransactionSellViewModel @Inject constructor(
    private val currentUserUseCase: CurrentUserUseCase,
    private val formatUseCase: FormatUseCase,
    private val userRepository: UserRepositoryImpl
) : ViewModel() {
    var viewState by mutableStateOf(MarketDetailTransactionSellViewState())
        private set

    var viewEffect = MutableSharedFlow<MarketDetailTransactionSellViewEffect>()
        private set

    private var user: User? = null
    private var newMainTransaction: Transaction? = null
    private var newCommissionTransaction: Transaction? = null

    init {
        viewModelScope.launch {
            currentUserUseCase.getCurrentUser()
                .collectLatest { user ->
                    this@MarketDetailTransactionSellViewModel.user = user
                    refresh()
                }
        }
    }

    fun onEvent(event: MarketDetailTransactionSellEvent) {
        when (event) {
            MarketDetailTransactionSellEvent.BackButtonClicked -> backButtonClicked()
            is MarketDetailTransactionSellEvent.InitTransaction -> initTransaction(
                event.idCoin,
                event.userCoinForSell,
                event.priceCurrency
            )
        }
    }

    private fun initTransaction(idCoin: String, userCoinForSell: Double, priceCurrency: Double) {
        newMainTransaction = Transaction(
            status = StatusTransaction.Process,
            type = TypeTransaction.Sell(userCoinForSell, priceCurrency, idCoin),
            id = "",
            data = LocalDateTime.now()
        )

        newCommissionTransaction = Transaction(
            status = StatusTransaction.Process,
            type = TypeTransaction.CashBalance(priceCurrency * Consts.COMMISSION),
            id = "",
            data = LocalDateTime.now()
        )
        val commision = priceCurrency * Consts.COMMISSION

        viewState = viewState.copy(
            transaction = newMainTransaction ?: return,
            currencyForBuy = formatUseCase.getFormatBalance(priceCurrency),
            commission = formatUseCase.getFormatBalance(commision),
            total = formatUseCase.getFormatBalance(priceCurrency - commision)
        )
        refresh()
    }

    private fun backButtonClicked() {
        viewModelScope.launch {
            viewEffect.emit(MarketDetailTransactionSellViewEffect.MoveReturn)
        }
    }

    private fun refresh() {
        val user = user ?: return
        val mainTransaction = newMainTransaction ?: return
        val commissionTransaction = newCommissionTransaction ?: return

        viewState = viewState.copy(isLoading = true)

        viewModelScope.launch {

            val flowAddBalance = userRepository.sellActive(
                user = user,
                idCoin = (mainTransaction.type as TypeTransaction.Sell).id,
                userCoinForSell = mainTransaction.type.coin,
                priceCurrency = mainTransaction.type.currency - mainTransaction.type.currency * Consts.COMMISSION
            )

            flowAddBalance.collect { status ->
                when (status) {
                    is StatusProcessTransaction.Init -> {
                        val newTransaction =
                            viewState.transaction.copy(id = status.code)
                        viewState = viewState.copy(transaction = newTransaction)
                    }
                    StatusProcessTransaction.Success -> {
                        newMainTransaction = null
                        newCommissionTransaction = null
                        val newTransaction =
                            viewState.transaction.copy(status = StatusTransaction.Success)
                        viewState = viewState.copy(transaction = newTransaction)
                        viewState = viewState.copy(isLoading = false)
                        delay(1000)
                        viewEffect.emit(MarketDetailTransactionSellViewEffect.MoveReturn)
                    }
                    StatusProcessTransaction.Failed -> {
                        newMainTransaction = null
                        newCommissionTransaction = null
                        val newTransaction =
                            viewState.transaction.copy(status = StatusTransaction.Fail)
                        viewState = viewState.copy(transaction = newTransaction)
                        viewState = viewState.copy(isLoading = false)
                        viewEffect.emit(
                            MarketDetailTransactionSellViewEffect.ShowError(
                                ERROR_TRANSACTION
                            )
                        )
                        delay(1000)
                        viewEffect.emit(MarketDetailTransactionSellViewEffect.MoveReturn)
                    }
                }
            }
        }
    }

}