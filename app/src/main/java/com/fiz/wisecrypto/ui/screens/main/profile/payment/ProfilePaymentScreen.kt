package com.fiz.wisecrypto.ui.screens.main.profile.payment

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.components.WiseCryptoButton
import com.fiz.wisecrypto.ui.screens.main.components.MainColumn
import com.fiz.wisecrypto.ui.screens.main.components.PaymentItem
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
                    PaymentItem(
                        payment = payment,
                        iconId = R.drawable.payment_ic_delete,
                        colorIcon = Red,
                        actionOnClick = {
                            viewModel.onEvent(
                                ProfilePaymentEvent.DeletePayIconClicked(
                                    index
                                )
                            )
                        }
                    )
                }
            }


        }

        Spacer(modifier = Modifier.weight(1f))

        WiseCryptoButton(
            text = R.string.payment_add_new_pay,
            onClick = { viewModel.onEvent(ProfilePaymentEvent.AddPayButtonClicked) })
    }
}


