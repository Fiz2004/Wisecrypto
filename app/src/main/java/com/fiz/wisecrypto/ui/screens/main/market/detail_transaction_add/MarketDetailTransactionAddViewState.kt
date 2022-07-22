package com.fiz.wisecrypto.ui.screens.main.market.detail_transaction_add

import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.domain.models.transaction.StatusTransaction
import com.fiz.wisecrypto.domain.models.transaction.Transaction
import com.fiz.wisecrypto.domain.models.transaction.TypeTransaction
import com.fiz.wisecrypto.ui.screens.main.profile.payment.models.Payment
import org.threeten.bp.LocalDateTime

data class MarketDetailTransactionAddViewState(
    val transaction: Transaction = Transaction(
        StatusTransaction.Process, TypeTransaction.AddBalance(0.0), "",
        LocalDateTime.now()
    ),
    val payment: Payment = paymentsList.first(),
    val number: String = "234181230834",
    val openInfo1: Boolean = false,
    val openInfo2: Boolean = false,
    val openInfo3: Boolean = false,

    val isLoading: Boolean = false,

    )


private val paymentsList = listOf(
    Payment(R.drawable.payment_ic_ovo, "Ovo", "081264950021"),
    Payment(R.drawable.payment_ic_gopay, "Gopay", "081264950021"),
    Payment(R.drawable.payment_ic_mandiri, "Mandiri", "144202839134"),
)