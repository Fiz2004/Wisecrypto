package com.fiz.wisecrypto.domain.models

data class User(
    val fullName: String = "",
    val numberPhone: String = "",
    val userName: String = "",
    val email: String = "",
    val photo: String = "",
    val balance: Double = 0.0
)