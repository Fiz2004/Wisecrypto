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
    var balance: Long = 0,
    var watchList: List<String> = listOf(),
    @Ignore
    var actives: List<ActiveEntity> = listOf(),
    @Ignore
    var transactions: List<TransactionEntity> = listOf()
) {
    fun toUser(): User {
        return User.create(
            userName = userName,
            email = email,
            numberPhone = numberPhone,
            fullName = fullName,
            balance = balance,
            watchList = watchList,
            actives = actives.map { it.toActive() },
            transactions = transactions.map { it.toTransaction() }
        )
    }
}

fun User.toUserEntity(password: String): UserEntity {
    return UserEntity(
        fullName = this.fullName,
        numberPhone = this.numberPhone,
        userName = this.userName,
        email = this.email,
        password = password.trim().lowercase(),
        watchList = this.watchList,
        balance = this.balanceForSaveEntity,
        actives = this.actives.map { it.toActiveEntity(email.trim().lowercase()) },
        transactions = this.transactions.map { it.toTransactionEntity(this.email) }
    )
}