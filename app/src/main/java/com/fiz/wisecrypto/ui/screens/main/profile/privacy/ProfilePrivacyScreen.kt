package com.fiz.wisecrypto.ui.screens.main.profile.privacy

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.components.PrimaryButton
import com.fiz.wisecrypto.ui.components.TextFieldWithHeader
import com.fiz.wisecrypto.ui.screens.main.MainViewModel
import com.fiz.wisecrypto.ui.screens.main.components.Toolbar


@Composable
fun ProfilePrivacyScreen(
    MainViewModel: MainViewModel = viewModel(),
    viewModel: ProfilePrivacyViewModel = viewModel(),
    moveReturn: () -> Unit,
) {

    val context = LocalContext.current

    val viewState = viewModel.viewState
    val mainViewState = MainViewModel.viewState
    val viewEffect = viewModel.viewEffect

    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                ProfilePrivacyViewEffect.MoveReturn -> {
                    moveReturn()
                }
                is ProfilePrivacyViewEffect.ShowToast -> {
                    val text = context.getString(effect.message)
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp)
    ) {

        Spacer(
            modifier = Modifier
                .windowInsetsTopHeight(WindowInsets.statusBars)
        )

        Toolbar(
            title = stringResource(R.string.privacy_title),
            onClickBackButton = { viewModel.onEvent(ProfilePrivacyEvent.BackButtonClicked) }
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextFieldWithHeader(
            textHeader = stringResource(R.string.email_title),
            text = viewState.email,
            onValueChange = { viewModel.onEvent(ProfilePrivacyEvent.EmailChanged(it)) },
            textHint = stringResource(R.string.email_hint),
        )

        TextFieldWithHeader(
            textHeader = stringResource(R.string.privacy_old_password_title),
            text = viewState.oldPassword,
            onValueChange = { viewModel.onEvent(ProfilePrivacyEvent.OldPasswordChanged(it)) },
            textHint = stringResource(R.string.privacy_old_password_hint),
            password = true
        )

        TextFieldWithHeader(
            textHeader = stringResource(R.string.privacy_new_password_title),
            text = viewState.newPassword,
            onValueChange = { viewModel.onEvent(ProfilePrivacyEvent.NewPasswordChanged(it)) },
            textHint = stringResource(R.string.privacy_new_password_hint),
            password = true
        )

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            text = R.string.privacy_save,
            onClick = { viewModel.onEvent(ProfilePrivacyEvent.SaveButtonClicked) })
    }
}


