package com.fiz.wisecrypto.ui.screens.main.home.notification

import com.fiz.wisecrypto.ui.screens.main.home.notification.models.Notification
import com.fiz.wisecrypto.ui.screens.main.home.notification.models.StatusOperation
import com.fiz.wisecrypto.ui.screens.main.home.notification.models.StatusPortfolio
import com.fiz.wisecrypto.ui.screens.main.home.notification.models.TypeNotification
import org.threeten.bp.LocalDateTime

data class HomeNotificationViewState(
    val fullName: String = "",
    val photo: String = "",
    val notifications: List<Notification> = notificationsList
)

val notificationsList = listOf(
    Notification(
        TypeNotification.Portfolio(StatusPortfolio.Increased, 1.1, "биткойн"),
        LocalDateTime.of(2021, 11, 29, 13, 0)
    ),
    Notification(
        TypeNotification.Transaction(StatusOperation.Success, 0.00001, "биткойна"),
        LocalDateTime.of(2021, 11, 29, 13, 0)
    ),
    Notification(
        TypeNotification.Balance(StatusOperation.Process, 10, "$"),
        LocalDateTime.of(2021, 11, 29, 13, 0)
    ),
    Notification(
        TypeNotification.Balance(StatusOperation.Success, 10, "$"),
        LocalDateTime.of(2021, 11, 29, 13, 0)
    ),
    Notification(
        TypeNotification.Balance(StatusOperation.Fail, 10, "$"),
        LocalDateTime.of(2021, 11, 29, 13, 0)
    ),
)