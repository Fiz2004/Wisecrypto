package com.fiz.wisecrypto.ui.screens.main.market.detail_transaction_cash

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
import com.fiz.wisecrypto.util.ERROR_TRANSACTION
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MarketDetailTransactionCashViewModel @Inject constructor(
    private val currentUserUseCase: CurrentUserUseCase,
    private val formatUseCase: FormatUseCase,
    private val userRepository: UserRepositoryImpl
) : ViewModel() {
    var viewState by mutableStateOf(MarketDetailTransactionCashViewState())
        private set

    var viewEffect = MutableSharedFlow<MarketDetailTransactionCashViewEffect>()
        private set

    private var user: User? = null
    private var newMainTransaction: Transaction? = null
    private var newCommissionTransaction: Transaction? = null

    init {
        viewModelScope.launch {
            currentUserUseCase.getCurrentUser()
                .collectLatest { user ->
                    this@MarketDetailTransactionCashViewModel.user = user
                    refresh()
                }
        }
    }

    fun onEvent(event: MarketDetailTransactionCashEvent) {
        when (event) {
            MarketDetailTransactionCashEvent.BackButtonClicked -> backButtonClicked()
            MarketDetailTransactionCashEvent.CancelButtonClicked -> cancelButtonClicked()
            is MarketDetailTransactionCashEvent.InitTransaction -> initTransaction(
                event.currency,
                event.commission
            )
        }
    }

    private fun initTransaction(currency: Double, commission: Double) {
        newMainTransaction = Transaction(
            status = StatusTransaction.Process,
            type = TypeTransaction.CashBalance(currency),
            id = "",
            data = LocalDateTime.now()
        )

        newCommissionTransaction = Transaction(
            status = StatusTransaction.Process,
            type = TypeTransaction.CashBalance(commission),
            id = "",
            data = LocalDateTime.now()
        )

        viewState = viewState.copy(transaction = newMainTransaction ?: return)
        refresh()
    }

    private fun cancelButtonClicked() {
        viewModelScope.launch {
            userRepository.cancelAddTransaction()
        }
    }

    private fun backButtonClicked() {
        viewModelScope.launch {
            viewEffect.emit(MarketDetailTransactionCashViewEffect.MoveReturn)
        }
    }

    private fun refresh() {
        val user = user ?: return
        val mainTransaction = newMainTransaction ?: return
        val commissionTransaction = newCommissionTransaction ?: return

        viewState = viewState.copy(isLoading = true)

        viewModelScope.launch {
            val flowAddBalance = userRepository.cashBalance(
                user = user,
                currency = (mainTransaction.type as TypeTransaction.CashBalance).value,
                comission = (commissionTransaction.type as TypeTransaction.CashBalance).value
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
                        viewEffect.emit(MarketDetailTransactionCashViewEffect.MoveReturn)
                    }
                    StatusProcessTransaction.Failed -> {
                        newMainTransaction = null
                        newCommissionTransaction = null
                        val newTransaction =
                            viewState.transaction.copy(status = StatusTransaction.Fail)
                        viewState = viewState.copy(transaction = newTransaction)
                        viewState = viewState.copy(isLoading = false)
                        viewEffect.emit(
                            MarketDetailTransactionCashViewEffect.ShowError(
                                ERROR_TRANSACTION
                            )
                        )
                        delay(1000)
                        viewEffect.emit(MarketDetailTransactionCashViewEffect.MoveReturn)
                    }
                }
            }
        }
    }

}