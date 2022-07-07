package com.fiz.wisecrypto.ui.screens.main.profile.payment

import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.profile.payment.models.Payment

data class ProfilePaymentViewState(
    val payments: List<Payment> = paymentsList,
)

private val paymentsList = listOf(
    Payment(R.drawable.payment_ic_ovo, "Ovo", "081264950021"),
    Payment(R.drawable.payment_ic_gopay, "Gopay", "081264950021"),
    Payment(R.drawable.payment_ic_mandiri, "Mandiri", "144202839134"),
)