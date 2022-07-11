package com.fiz.wisecrypto.data.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.fiz.wisecrypto.domain.models.User

@Entity
data class UserEntity(
    @PrimaryKey
    var email: String = "",
    var fullName: String = "",
    var numberPhone: String = "",
    var userName: String = "",
    var password: String = "",
    var balance: Double = 0.0,
    var watchList: List<String> = listOf(),
    @Ignore
    var actives: List<ActiveEntity> = listOf()
) {
    fun toUser(): User {
        return User(
            userName = userName,
            email = email,
            numberPhone = numberPhone,
            fullName = fullName,
            balance = balance,
            watchList = watchList,
            actives = actives.map { it.toActive() }
        )
    }
}