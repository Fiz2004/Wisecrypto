package com.fiz.wisecrypto.domain.models.transaction

import org.threeten.bp.LocalDateTime

data class Transaction(
    val status: StatusTransaction,
    val type: TypeTransaction,
    val textDescription: String,
    val id: String,
    val data: LocalDateTime
)