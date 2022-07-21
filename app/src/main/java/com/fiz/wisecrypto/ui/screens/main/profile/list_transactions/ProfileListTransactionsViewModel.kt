package com.fiz.wisecrypto.ui.screens.main.profile.list_transactions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.domain.models.User
import com.fiz.wisecrypto.domain.models.transaction.TypeTransaction
import com.fiz.wisecrypto.domain.use_case.CurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileListTransactionsViewModel @Inject constructor(
    private val currentUserUseCase: CurrentUserUseCase,

    ) : ViewModel() {
    var viewState by mutableStateOf(
        ProfileListTransactionsViewState()
    )
        private set

    var viewEffect = MutableSharedFlow<ProfileListTransactionsViewEffect>()
        private set

    private var user: User? = null

    init {
        viewModelScope.launch {
            currentUserUseCase.getCurrentUser()
                .collectLatest { user ->
                    this@ProfileListTransactionsViewModel.user = user
                    refresh()
                }
        }
    }

    fun onEvent(event: ProfileListTransactionsEvent) {
        when (event) {
            ProfileListTransactionsEvent.BackButtonClicked -> backButtonClicked()
            is ProfileListTransactionsEvent.ChipClicked -> chipClicked(event.number)
        }
    }

    private fun chipClicked(index: Int) {
        viewState = viewState.copy(selectedChipNumber = index)
        refresh()
    }

    private fun backButtonClicked() {
        viewModelScope.launch {
            viewEffect.emit(ProfileListTransactionsViewEffect.MoveReturn)
        }
    }

    private fun refresh() {
        user?.let { user ->
            viewModelScope.launch {
                val index = viewState.selectedChipNumber
                val transactions = user.transactions.filter {
                    when (index) {
                        0 -> true
                        1 -> it.type is TypeTransaction.AddBalance || it.type is TypeTransaction.CashBalance
                        2 -> it.type is TypeTransaction.Buy
                        else -> it.type is TypeTransaction.Sell
                    }
                }
                viewState = viewState.copy(transactions = transactions)
            }
        }
    }
}