package com.fiz.wisecrypto.ui.screens.login.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.login.components.*
import com.fiz.wisecrypto.ui.theme.hint

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = viewModel(),
    moveSignInScreen: () -> Unit = {},
    moveSignUpNextScreen: () -> Unit = {},

    ) {
    val viewState = viewModel.viewState
    val viewEffect = viewModel.viewEffect

    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                SignUpViewEffect.MoveSignInScreen -> {
                    moveSignInScreen()
                }
                SignUpViewEffect.MoveSignUpNextScreen -> {
                    moveSignUpNextScreen()
                }
            }
        }
    }

    MainColumn {

        LogoItem()

        TitleAndGreeting(textTitle = R.string.signup_title, textGreeting = R.string.slogan)

        TextFieldWithHeader(
            text = viewState.userName,
            onValueChange = { viewModel.onEvent(SignUpEvent.UserNameChanged(it)) },
            textHeader = stringResource(R.string.signup_fullname_title),
            textHint = stringResource(R.string.signup_fullname_hint)
        )

        TextFieldWithHeader(
            text = viewState.numberPhone,
            onValueChange = { viewModel.onEvent(SignUpEvent.NumberPhoneChanged(it)) },
            textHeader = stringResource(R.string.signup_number_phone_title),
            textHint = stringResource(R.string.signup_number_phone_hint)
        )

        TextFieldWithHeader(
            text = viewState.fullName,
            onValueChange = { viewModel.onEvent(SignUpEvent.FullNameChanged(it)) },
            textHeader = stringResource(R.string.signup_username_title),
            textHint = stringResource(R.string.signup_username_hint)
        )

        PrimaryButton(
            text = R.string.signup_next,
            onClick = { viewModel.onEvent(SignUpEvent.NextButtonClicked) })

        TextExtraAction(
            infoText = R.string.signup_already_register,
            textAction = R.string.signup_signin,
            onClickAction = { viewModel.onEvent(SignUpEvent.SignInClicked) }
        )

        Spacer(modifier = Modifier.height(56.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.onEvent(SignUpEvent.SkipClicked) },
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.signup_skip),
                color = MaterialTheme.colorScheme.hint,
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(modifier = Modifier.width(4.dp))

            Icon(
                modifier = Modifier.size(10.dp),
                painter = painterResource(id = R.drawable.signup_ic_skip),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.hint
            )
        }
    }
}