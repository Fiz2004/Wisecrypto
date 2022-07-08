package com.fiz.wisecrypto.ui.screens.main.home.notification.models

import org.threeten.bp.LocalDateTime

data class Notification(
    val type: TypeNotification,
    val data: LocalDateTime
)