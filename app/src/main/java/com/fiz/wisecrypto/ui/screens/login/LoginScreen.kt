package com.fiz.wisecrypto.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.theme.WisecryptoTheme

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel()
) {
    val state = viewModel.viewState

    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(LoginEvent.StartScreen)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp)
    ) {

        Spacer(modifier = Modifier.padding(8.dp))

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .size(182.dp, 64.dp),
            painter = painterResource(R.drawable.login_pic_logo),
            contentDescription = null
        )

        Spacer(
            modifier = Modifier
                .padding(26.dp)
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.login_welcome),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.splash_title),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.displaySmall
        )

        Spacer(modifier = Modifier.padding(26.dp))

        LoginTextFieldWithHeader(
            textHeader = stringResource(R.string.login_email),
            textHint = stringResource(R.string.login_email_hint)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        LoginTextFieldWithHeader(
            textHeader = stringResource(R.string.login_password),
            textHint = stringResource(R.string.login_password_hint)
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.login_forgot_password),
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Button(
            modifier = Modifier
                .height(42.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(4.dp),
            onClick = { /*TODO*/ }) {
            Text(
                text = stringResource(R.string.login_signin)
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.login_noaccount),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.padding(4.dp))

            Text(
                text = stringResource(R.string.login_signup),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Composable
fun LoginTextFieldWithHeader(
    textHeader: String,
    textHint: String
) {
    HeaderTextField(textHeader)

    Spacer(modifier = Modifier.padding(4.dp))

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = "",
        onValueChange = {},
        placeholder = {
            Text(
                text = textHint,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun HeaderTextField(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier
            .fillMaxWidth(),
        text = text,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.titleLarge
    )
}

@Preview(
    showBackground = true,
    widthDp = 375,
    heightDp = 812
)
@Composable
private fun DefaultPreview() {
    WisecryptoTheme {
        LoginScreen()
    }
}