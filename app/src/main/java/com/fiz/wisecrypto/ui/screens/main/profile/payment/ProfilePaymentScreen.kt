package com.fiz.wisecrypto.ui.screens.main.profile.payment

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.components.WiseCryptoButton
import com.fiz.wisecrypto.ui.screens.main.components.MainColumn
import com.fiz.wisecrypto.ui.theme.Red

@Composable
fun ProfilePaymentScreen(
    viewModel: ProfilePaymentViewModel = viewModel(),
    moveReturn: () -> Unit,
) {

    val context = LocalContext.current

    val viewState = viewModel.viewState
    val viewEffect = viewModel.viewEffect

    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                ProfilePaymentViewEffect.MoveReturn -> {
                    moveReturn()
                }
                is ProfilePaymentViewEffect.ShowToast -> {
                    val text = context.getString(effect.message)
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    MainColumn(
        textToolbar = stringResource(R.string.payment_title),
        onClickBackButton = { viewModel.onEvent(ProfilePaymentEvent.BackButtonClicked) }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            viewState.payments.forEachIndexed { index, payment ->
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.onPrimary,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(vertical = 12.dp)
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            modifier = Modifier
                                .size(48.dp),
                            painter = painterResource(id = payment.icon),
                            contentDescription = null,
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = payment.name,
                                style = MaterialTheme.typography.headlineLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = payment.number,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    viewModel.onEvent(
                                        ProfilePaymentEvent.DeletePayIconClicked(
                                            index
                                        )
                                    )
                                },
                            painter = painterResource(id = R.drawable.payment_ic_delete),
                            contentDescription = null,
                            tint = Red
                        )
                    }

                }
            }


        }

        Spacer(modifier = Modifier.weight(1f))

        WiseCryptoButton(
            text = R.string.payment_add_new_pay,
            onClick = { viewModel.onEvent(ProfilePaymentEvent.AddPayButtonClicked) })
    }
}


