package com.fiz.wisecrypto.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.fiz.wisecrypto.data.database.dao.UserDao
import com.fiz.wisecrypto.data.entity.ActiveEntity
import com.fiz.wisecrypto.data.entity.TransactionEntity
import com.fiz.wisecrypto.data.entity.UserEntity
import com.fiz.wisecrypto.domain.models.transaction.StatusTransaction
import com.fiz.wisecrypto.domain.models.transaction.TypeTransaction
import org.threeten.bp.LocalDateTime

@Database(
    entities = [UserEntity::class, ActiveEntity::class, TransactionEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract fun userDao(): UserDao
}

class Converters {
    @TypeConverter
    fun fromList(value: List<String>?): String? {
        return value?.joinToString(SEPARATOR)
    }

    @TypeConverter
    fun toList(list: String?): List<String> {
        return list?.split(SEPARATOR) ?: listOf()
    }

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime): String {
        return value.toString()
    }

    @TypeConverter
    fun toLocalDateTime(date: String): LocalDateTime {
        return LocalDateTime.parse(date)
    }


    @TypeConverter
    fun fromStatusTransaction(value: StatusTransaction): String {
        return value.name
    }

    @TypeConverter
    fun toStatusTransaction(name: String): StatusTransaction {
        return StatusTransaction.valueOf(name)
    }


    @TypeConverter
    fun fromTypeTransaction(value: TypeTransaction): String {
        return when (value) {
            is TypeTransaction.Balance -> "Balance${SEPARATOR}${value.value}"
            is TypeTransaction.Buy -> "Buy$SEPARATOR${value.currency}$SEPARATOR${value.coin}"
            is TypeTransaction.Sell -> "Sell$SEPARATOR${value.coin}$SEPARATOR${value.currency}"
        }
    }

    @TypeConverter
    fun toTypeTransaction(type: String): TypeTransaction {
        val t = type.split(SEPARATOR)
        return when (t[0]) {
            "Balance" -> TypeTransaction.Balance(t[1].toDouble())
            "Buy" -> TypeTransaction.Buy(t[1].toDouble(), t[2].toDouble())
            else -> TypeTransaction.Sell(t[1].toDouble(), t[2].toDouble())
        }
    }

    companion object {
        const val SEPARATOR = ";"
    }
}