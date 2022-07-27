package com.fiz.wisecrypto.ui.screens.main.market.detail_transaction_cash

import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.domain.models.transaction.StatusTransaction
import com.fiz.wisecrypto.domain.models.transaction.Transaction
import com.fiz.wisecrypto.domain.models.transaction.TypeTransaction
import com.fiz.wisecrypto.ui.screens.main.profile.payment.models.Payment
import org.threeten.bp.LocalDateTime

data class MarketDetailTransactionCashViewState(
    val transaction: Transaction = Transaction(
        StatusTransaction.Process, TypeTransaction.CashBalance(0.0), "",
        LocalDateTime.now()
    ),
    val payment: Payment = paymentsList.first(),

    val isLoading: Boolean = false,

    )


private val paymentsList = listOf(
    Payment(R.drawable.payment_ic_ovo, "Ovo", "081264950021"),
    Payment(R.drawable.payment_ic_gopay, "Gopay", "081264950021"),
    Payment(R.drawable.payment_ic_mandiri, "Mandiri", "144202839134"),
)