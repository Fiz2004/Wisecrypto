package com.fiz.wisecrypto.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fiz.wisecrypto.domain.models.User

@Entity
class UserEntity (
    val fullName: String = "",
    val numberPhone: String = "",
    val userName: String = "",
    @PrimaryKey
    val email: String = "",
    val password: String = "",
) {
    fun toUser(): User {
        return User(
            userName = userName,
            email = email,
            numberPhone = numberPhone,
            fullName=fullName
        )
    }
}