package com.fiz.wisecrypto.ui.screens.main.profile.payment

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
import javax.inject.Inject

@HiltViewModel
class ProfilePaymentViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl,
    private val userRepository: UserRepositoryImpl,

    ) : ViewModel() {
    var viewState by mutableStateOf(ProfilePaymentViewState())
        private set

    var viewEffect = MutableSharedFlow<ProfilePaymentViewEffect>()
        private set

    fun onEvent(event: ProfilePaymentEvent) {
        when (event) {
            ProfilePaymentEvent.BackButtonClicked -> backButtonClicked()
            ProfilePaymentEvent.AddPayButtonClicked -> saveButtonClicked()
            is ProfilePaymentEvent.DeletePayIconClicked -> deletePayIconClicked(event.index)
        }
    }

    private fun deletePayIconClicked(index: Int) {
        viewModelScope.launch {
            val newPayment = viewState.payments.toMutableList()
            newPayment.removeAt(index)
            viewState = viewState.copy(
                payments = newPayment
            )
        }
    }

    private fun saveButtonClicked() {
        viewModelScope.launch {

        }
    }

    private fun backButtonClicked() {
        viewModelScope.launch {
            viewEffect.emit(ProfilePaymentViewEffect.MoveReturn)
        }
    }
}