package com.fiz.wisecrypto.ui.screens.main.profile.list_transactions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.repositories.AuthRepositoryImpl
import com.fiz.wisecrypto.data.repositories.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

private val transactionsDefault = listOf(
    Transaction(
        StatusTransaction.Process,
        TypeTransaction.Balance(5.0),
        "5$",
        "TS-10283",
        LocalDateTime.of(2021, 11, 29, 12, 30)
    ),
    Transaction(
        StatusTransaction.Success,
        TypeTransaction.Balance(5.0),
        "5$",
        "TS-24932",
        LocalDateTime.of(2021, 11, 29, 12, 30)
    ),
    Transaction(
        StatusTransaction.Fail,
        TypeTransaction.Balance(5.0),
        "5$",
        "TS-32418",
        LocalDateTime.of(2021, 11, 29, 12, 30)
    ),
    Transaction(
        StatusTransaction.Success,
        TypeTransaction.Sell(5.0),
        "0.000001 BTC -> 5$",
        "TS-12342",
        LocalDateTime.of(2021, 11, 29, 12, 30)
    ),
    Transaction(
        StatusTransaction.Success,
        TypeTransaction.Buy(5.0),
        "5$ -> 0.000001 BTC",
        "TS-38284",
        LocalDateTime.of(2021, 11, 29, 12, 30)
    ),
)

@HiltViewModel
class ProfileListTransactionsViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl,
    private val userRepository: UserRepositoryImpl,

    ) : ViewModel() {
    var viewState by mutableStateOf(
        ProfileListTransactionsViewState(
            transactions = transactionsDefault
        )
    )
        private set

    var viewEffect = MutableSharedFlow<ProfileListTransactionsViewEffect>()
        private set

    //    init {
//        viewModelScope.launch {
//            val email = authRepository.getAuthEmail()
//            if (email == null) {
//                viewEffect.emit(ProfileListTransactionsViewEffect.MoveSignIn)
//            } else {
//                val user = userRepository.getUserInfo(email)
//                if (user == null)
//                    viewEffect.emit(HomeNotificationViewEffect.MoveSignIn)
//                else
//                    viewState = viewState.copy(fullName = user.fullName)
//            }
//        }
//    }
//
    fun onEvent(event: ProfileListTransactionsEvent) {
        when (event) {
            ProfileListTransactionsEvent.BackButtonClicked -> backButtonClicked()
            is ProfileListTransactionsEvent.ChipClicked -> chipClicked(event.number)
        }
    }

    private fun chipClicked(index: Int) {
        viewModelScope.launch {
            viewState = viewState.copy(selectedChipNumber = index)
            val transactions = transactionsDefault.filter {
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