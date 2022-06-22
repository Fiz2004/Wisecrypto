package com.fiz.wisecrypto.ui.screens.login.signup

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.login.components.*
import com.fiz.wisecrypto.ui.theme.skip

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = viewModel()
) {
    val state = viewModel.viewState

    MainColumn {

        LogoItem()

        TitleAndGreeting(textTitle = R.string.signup_title, textGreeting = R.string.slogan)

        TextFieldWithHeader(
            text = "",
            onValueChange = {},
            textHeader = stringResource(R.string.signup_fullname_title),
            textHint = stringResource(R.string.signup_fullname_hint)
        )

        TextFieldWithHeader(
            text = "",
            onValueChange = {},
            textHeader = stringResource(R.string.signup_number_phone_title),
            textHint = stringResource(R.string.signup_number_phone_hint)
        )

        TextFieldWithHeader(
            text = "",
            onValueChange = {},
            textHeader = stringResource(R.string.signup_username_title),
            textHint = stringResource(R.string.signup_username_hint)
        )

        PrimaryButton(text = R.string.signup_next, onClick = {})

        TextExtraAction(
            infoText = R.string.signup_already_register,
            textAction = R.string.signup_signin,
            onClickAction = {}
        )

        Spacer(modifier = Modifier.height(56.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = stringResource(R.string.signup_skip),
                color = MaterialTheme.colorScheme.skip
            )
            Icon(
                painter = painterResource(id = R.drawable.signup_ic_skip),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.skip
            )
        }
    }
}