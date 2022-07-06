package com.fiz.wisecrypto.ui.screens.main.profile.privacy

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.data.repositories.AuthRepositoryImpl
import com.fiz.wisecrypto.data.repositories.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilePrivacyViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl,
    private val userRepository: UserRepositoryImpl,

    ) : ViewModel() {
    var viewState by mutableStateOf(ProfilePrivacyViewState())
        private set

    var viewEffect = MutableSharedFlow<ProfilePrivacyViewEffect>()
        private set

    init {
        viewModelScope.launch {
            val checkEmail = authRepository.getAuthEmail()
            checkEmail?.let {
                viewState = viewState.copy(email = it)
            }
        }
    }

    fun onEvent(event: ProfilePrivacyEvent) {
        when (event) {
            ProfilePrivacyEvent.BackButtonClicked -> backButtonClicked()
            is ProfilePrivacyEvent.EmailChanged -> emailChanged(event.value)
            is ProfilePrivacyEvent.NewPasswordChanged -> newPasswordChanged(event.value)
            is ProfilePrivacyEvent.OldPasswordChanged -> oldPasswordChanged(event.value)
            ProfilePrivacyEvent.SaveButtonClicked -> saveButtonClicked()
        }
    }

    private fun saveButtonClicked() {
        viewModelScope.launch {
            val checkEmail = authRepository.getAuthEmail() ?: ""
            if (userRepository.changeEmailPassword(
                    checkEmail,
                    viewState.email,
                    viewState.oldPassword,
                    viewState.newPassword
                )
            ) {
                authRepository.authCompleted(viewState.email)
                viewEffect.emit(ProfilePrivacyViewEffect.ShowToast(R.string.privacy_change_ok))
                viewEffect.emit(ProfilePrivacyViewEffect.MoveReturn)
            } else {
                viewEffect.emit(ProfilePrivacyViewEffect.ShowToast(R.string.privacy_change_error))
            }
        }
    }

    private fun oldPasswordChanged(value: String) {
        viewState = viewState.copy(oldPassword = value)
    }

    private fun newPasswordChanged(value: String) {
        viewState = viewState.copy(newPassword = value)
    }

    private fun emailChanged(value: String) {
        viewState = viewState.copy(email = value)
    }

    private fun backButtonClicked() {
        viewModelScope.launch {
            viewEffect.emit(ProfilePrivacyViewEffect.MoveReturn)
        }
    }
}