package com.fiz.wisecrypto.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.fiz.wisecrypto.domain.models.StatusTransaction
import com.fiz.wisecrypto.domain.models.Transaction
import com.fiz.wisecrypto.domain.models.TypeTransaction
import org.threeten.bp.LocalDateTime

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
data class TransactionEntity(
    val status: StatusTransaction,
    val type: TypeTransaction,
    val textDescription: String,
    @PrimaryKey
    val id: String,
    val emailId: String = "",
    val data: LocalDateTime
) {
    fun toTransaction(): Transaction {
        return Transaction(
            status = status,
            type = type,
            textDescription = textDescription,
            id = id,
            data = data
        )
    }
}