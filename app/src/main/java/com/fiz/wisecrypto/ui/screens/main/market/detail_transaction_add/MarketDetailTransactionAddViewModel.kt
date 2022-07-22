package com.fiz.wisecrypto.ui.screens.main.market.detail_transaction_add

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
class MarketDetailTransactionAddViewModel @Inject constructor(
    private val currentUserUseCase: CurrentUserUseCase,
    private val formatUseCase: FormatUseCase,
    private val userRepository: UserRepositoryImpl
) : ViewModel() {
    var viewState by mutableStateOf(MarketDetailTransactionAddViewState())
        private set

    var viewEffect = MutableSharedFlow<MarketDetailTransactionAddViewEffect>()
        private set

    private var user: User? = null
    private var newMainTransaction: Transaction? = null
    private var newCommissionTransaction: Transaction? = null

    init {
        viewModelScope.launch {
            currentUserUseCase.getCurrentUser()
                .collectLatest { user ->
                    this@MarketDetailTransactionAddViewModel.user = user
                    refresh()
                }
        }
    }

    fun onEvent(event: MarketDetailTransactionAddEvent) {
        when (event) {
            MarketDetailTransactionAddEvent.BackButtonClicked -> backButtonClicked()
            MarketDetailTransactionAddEvent.CancelButtonClicked -> cancelButtonClicked()
            MarketDetailTransactionAddEvent.CopyButtonClicked -> copyButtonClicked()
            is MarketDetailTransactionAddEvent.InitTransaction -> initTransaction(
                event.currency,
                event.commission
            )
            MarketDetailTransactionAddEvent.OpenInfo1Clicked -> openInfo1Clicked()
            MarketDetailTransactionAddEvent.OpenInfo2Clicked -> openInfo2Clicked()
            MarketDetailTransactionAddEvent.OpenInfo3Clicked -> openInfo3Clicked()
        }
    }

    private fun openInfo1Clicked() {
        viewState = viewState.copy(openInfo1 = !viewState.openInfo1)
    }

    private fun openInfo2Clicked() {
        viewState = viewState.copy(openInfo2 = !viewState.openInfo2)
    }

    private fun openInfo3Clicked() {
        viewState = viewState.copy(openInfo3 = !viewState.openInfo3)
    }

    private fun initTransaction(currency: Double, commission: Double) {
        newMainTransaction = Transaction(
            status = StatusTransaction.Process,
            type = TypeTransaction.AddBalance(currency),
            id = "",
            data = LocalDateTime.now()
        )

        newCommissionTransaction = Transaction(
            status = StatusTransaction.Process,
            type = TypeTransaction.AddBalance(commission),
            id = "",
            data = LocalDateTime.now()
        )

        viewState = viewState.copy(transaction = newMainTransaction ?: return)
        refresh()
    }

    private fun copyButtonClicked() {

    }

    private fun cancelButtonClicked() {

    }

    private fun backButtonClicked() {
        viewModelScope.launch {
            viewEffect.emit(MarketDetailTransactionAddViewEffect.MoveReturn)
        }
    }

    private fun refresh() {
        val user = user ?: return
        val mainTransaction = newMainTransaction ?: return
        val commissionTransaction = newCommissionTransaction ?: return

        viewState = viewState.copy(isLoading = true)

        viewModelScope.launch {
            val flowAddBalance = userRepository.addBalance(
                user = user,
                currency = (mainTransaction.type as TypeTransaction.AddBalance).value,
                comission = (commissionTransaction.type as TypeTransaction.AddBalance).value
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
                        viewEffect.emit(MarketDetailTransactionAddViewEffect.MoveReturn)
                    }
                    StatusProcessTransaction.Failed -> {
                        newMainTransaction = null
                        newCommissionTransaction = null
                        val newTransaction =
                            viewState.transaction.copy(status = StatusTransaction.Fail)
                        viewState = viewState.copy(transaction = newTransaction)
                        viewState = viewState.copy(isLoading = false)
                        viewEffect.emit(
                            MarketDetailTransactionAddViewEffect.ShowError(
                                ERROR_TRANSACTION
                            )
                        )
                        delay(1000)
                        viewEffect.emit(MarketDetailTransactionAddViewEffect.MoveReturn)
                    }
                }
            }
        }
    }

}