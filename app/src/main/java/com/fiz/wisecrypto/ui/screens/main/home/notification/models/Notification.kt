package com.fiz.wisecrypto.ui.screens.main.home.notification.models

import java.time.LocalDate

data class Notification(
    val status: StatusNotification,
    val type: TypeNotification,
    val data: LocalDate
)