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
    val count: Double = 0.0,
    val priceForBuy: Double = 0.0
) {
    fun toActive(): Active {
        return Active(
            id = id,
            count = count,
            priceForBuy = priceForBuy
        )
    }
}
