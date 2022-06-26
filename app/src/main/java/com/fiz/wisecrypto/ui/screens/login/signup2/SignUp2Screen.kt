package com.fiz.wisecrypto.ui.screens.login.signup2

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.components.MainColumn
import com.fiz.wisecrypto.ui.screens.login.components.*
import com.fiz.wisecrypto.ui.screens.login.signup.SignUpViewState
import com.fiz.wisecrypto.ui.screens.login.signup2.components.TextPrivacy
import com.fiz.wisecrypto.ui.theme.borderCheckedBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp2Screen(
    viewModel: SignUp2ViewModel = viewModel(),
    signUpViewState: SignUpViewState = SignUpViewState(),
    moveSignInScreen: () -> Unit = {},
    showTermsAndConditions: () -> Unit = { },
    showPrivacyPolicy: () -> Unit = { },
    showContentPolicy: () -> Unit = { },
) {
    val context = LocalContext.current

    val viewState = viewModel.viewState
    val viewEffect = viewModel.viewEffect

    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                SignUp2ViewEffect.MoveSignInScreen -> {
                    moveSignInScreen()
                }
                SignUp2ViewEffect.ShowContentPolicy -> {
                    showTermsAndConditions()
                }
                SignUp2ViewEffect.ShowPrivacyPolicy -> {
                    showPrivacyPolicy()
                }
                SignUp2ViewEffect.ShowTermsAndConditions -> {
                    showContentPolicy()
                }
                is SignUp2ViewEffect.ShowError -> {
                    val errorText = context.getString(effect.textMessage)
                    Toast.makeText(context, errorText, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    MainColumn {

        LogoItem()

        TitleAndGreeting(textTitle = R.string.signup_title, textGreeting = R.string.slogan)

        TextFieldWithHeader(
            text = viewState.email,
            onValueChange = { viewModel.onEvent(SignUp2Event.EmailChanged(it)) },
            textHeader = stringResource(R.string.login_email_title),
            textHint = stringResource(R.string.login_email_hint)
        )

        TextFieldWithHeader(
            text = viewState.password,
            onValueChange = { viewModel.onEvent(SignUp2Event.PasswordChanged(it)) },
            textHeader = stringResource(R.string.login_password_title),
            textHint = stringResource(R.string.login_password_hint),
            password = true
        )

        TextFieldWithHeader(
            text = viewState.confirmPassword,
            onValueChange = { viewModel.onEvent(SignUp2Event.ConfirmPasswordChanged(it)) },
            textHeader = stringResource(R.string.signup2_confirm_password_title),
            textHint = stringResource(R.string.signup2_confirm_password_hint),
            password = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                modifier = Modifier.size(24.dp),
                checked = viewState.privacy,
                onCheckedChange = { viewModel.onEvent(SignUp2Event.PrivacyChanged) },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.borderCheckedBox,
                    uncheckedColor = MaterialTheme.colorScheme.borderCheckedBox
                )
            )
            Spacer(modifier = Modifier.width(8.dp))

            TextPrivacy(
                showTermsAndConditions = { viewModel.onEvent(SignUp2Event.TermsAndConditionsClicked) },
                showPrivacyPolicy = { viewModel.onEvent(SignUp2Event.PrivacyPolicyClicked) },
                showContentPolicy = { viewModel.onEvent(SignUp2Event.ContentPolicyClicked) }
            )
        }

        PrimaryButton(
            text = R.string.signup2_signup,
            onClick = { viewModel.onEvent(SignUp2Event.SignUpClicked(signUpViewState)) })

        TextExtraAction(
            infoText = R.string.signup_already_register,
            textAction = R.string.signup_signin,
            onClickAction = { viewModel.onEvent(SignUp2Event.SignInClicked) },
        )
    }
}

