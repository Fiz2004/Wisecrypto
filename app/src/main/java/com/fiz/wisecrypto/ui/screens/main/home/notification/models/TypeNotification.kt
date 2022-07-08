package com.fiz.wisecrypto.ui.screens.main.home.notification.models

sealed class TypeNotification {
    data class Portfolio(val status: StatusPortfolio, val percent: Double, val coin: String) :
        TypeNotification()

    data class Transaction(val status: StatusOperation, val value: Double, val coin: String) :
        TypeNotification()

    data class Balance(val status: StatusOperation, val value: Int, val currency: String) :
        TypeNotification()
}

