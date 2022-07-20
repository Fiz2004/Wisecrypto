package com.fiz.wisecrypto.domain.models.notification

import org.threeten.bp.LocalDateTime

data class Notification(
    val type: TypeNotification,
    val data: LocalDateTime
)