package com.fiz.wisecrypto.ui.screens.main.profile.list_transactions.models

import org.threeten.bp.LocalDateTime

data class Transaction(
    val status: StatusTransaction,
    val type: TypeTransaction,
    val textDescription: String,
    val id: String,
    val data: LocalDateTime
)