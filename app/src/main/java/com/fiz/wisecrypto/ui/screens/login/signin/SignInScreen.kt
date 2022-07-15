package com.fiz.wisecrypto.ui.screens.login.signin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.components.TextFieldWithHeader
import com.fiz.wisecrypto.ui.components.WiseCryptoButton
import com.fiz.wisecrypto.ui.screens.login.components.LogoItem
import com.fiz.wisecrypto.ui.screens.login.components.TextExtraAction
import com.fiz.wisecrypto.ui.screens.login.components.TitleAndGreeting

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = viewModel(),
    moveForgotPasswordScreen: () -> Unit = {},
    moveSignUpScreen: () -> Unit = {},
    moveMainContentScreen: () -> Unit = {},
) {
    val context = LocalContext.current

    val viewState = viewModel.viewState
    val viewEffect = viewModel.viewEffect

    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                SignInViewEffect.MoveForgotPasswordScreen -> {
                    moveForgotPasswordScreen()
                }
                SignInViewEffect.MoveSignUpScreen -> {
                    moveSignUpScreen()
                }
                SignInViewEffect.MoveMainContentScreen -> {
                    moveMainContentScreen()
                }
                is SignInViewEffect.ShowError -> {
                    val errorText = context.getString(effect.textMessage)
                    Toast.makeText(context, errorText, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {

        item {
            LogoItem()
        }

        item {
            TitleAndGreeting(textTitle = R.string.signin_welcome, textGreeting = R.string.slogan)
        }

        item {
            TextFieldWithHeader(
                text = viewState.email,
                onValueChange = { viewModel.onEvent(SignInEvent.EmailChanged(it)) },
                textHeader = stringResource(R.string.email_title),
                textHint = stringResource(R.string.email_hint)
            )
        }

        item {
            TextFieldWithHeader(
                text = viewState.password,
                onValueChange = { viewModel.onEvent(SignInEvent.PasswordChanged(it)) },
                textHeader = stringResource(R.string.login_password_title),
                textHint = stringResource(R.string.login_password_hint),
                password = true
            )
            Spacer(modifier = Modifier.height(32.dp))
        }

        item {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.onEvent(SignInEvent.ForgotPasswordClicked) },
                textAlign = TextAlign.Center,
                text = stringResource(R.string.signin_forgot_password),
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        item {
            WiseCryptoButton(
                text = R.string.signin_signin,
                onClick = { viewModel.onEvent(SignInEvent.SignInClicked) })

        }

        item {
            TextExtraAction(
                infoText = R.string.signin_noaccount,
                textAction = R.string.signin_signup,
                onClickAction = { viewModel.onEvent(SignInEvent.SignUpClicked) }
            )
        }
    }
}

