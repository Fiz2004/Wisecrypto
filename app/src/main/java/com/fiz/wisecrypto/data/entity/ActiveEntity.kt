package com.fiz.wisecrypto.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.fiz.wisecrypto.domain.models.Active

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["email"],
            childColumns = ["emailId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("emailId")]
)
data class ActiveEntity(
    @PrimaryKey
    val id: String = "",
    val emailId: String = "",
    var count: Long = 0,
    val priceForBuy: Double = 0.0
) {
    fun toActive(): Active {
        return Active.create(
            id = id,
            count = count,
            priceForBuy = priceForBuy
        )
    }
}

fun Active.toActiveEntity(email: String): ActiveEntity {
    return ActiveEntity(
        id = id,
        emailId = email,
        count = countEntity,
        priceForBuy = priceForBuy
    )
}