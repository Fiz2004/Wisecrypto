package com.fiz.wisecrypto.ui.screens.main.profile.list_transactions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.domain.models.TypeTransaction
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

    init {
        viewModelScope.launch {
            currentUserUseCase.getCurrentUser()
                .collectLatest { user ->
                    user ?: return@collectLatest

                    val transactions = user.transactions

                    viewState = viewState.copy(
                        transactions = transactions,
                    )
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
        viewModelScope.launch {
            viewState = viewState.copy(selectedChipNumber = index)
            val transactions = viewState.transactions.filter {
                if (index == 0) return@filter true
                index == 1 && it.type is TypeTransaction.Balance ||
                        index == 2 && it.type is TypeTransaction.Buy ||
                        index == 3 && it.type is TypeTransaction.Sell
            }
            viewState = viewState.copy(transactions = transactions)
        }
    }

    private fun backButtonClicked() {
        viewModelScope.launch {
            viewEffect.emit(ProfileListTransactionsViewEffect.MoveReturn)
        }
    }
}