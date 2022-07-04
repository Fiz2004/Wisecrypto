package com.fiz.wisecrypto.ui.screens.main.home.notification.models

import org.threeten.bp.LocalDateTime

data class Notification(
    val status: StatusNotification,
    val type: TypeNotification,
    val textTitle: String,
    val textDescription: String,
    val data: LocalDateTime
)