package com.fiz.wisecrypto.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fiz.wisecrypto.domain.models.User

@Entity
data class UserEntity(
    @PrimaryKey
    val email: String = "",
    val fullName: String = "",
    val numberPhone: String = "",
    val userName: String = "",
    val password: String = "",
    val balance: Double = 0.0,
) {
    fun toUser(): User {
        return User(
            userName = userName,
            email = email,
            numberPhone = numberPhone,
            fullName = fullName,
            balance = balance
        )
    }
}