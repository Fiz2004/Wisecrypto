package com.fiz.wisecrypto.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.fiz.wisecrypto.data.database.dao.UserDao
import com.fiz.wisecrypto.data.entity.ActiveEntity
import com.fiz.wisecrypto.data.entity.UserEntity

@Database(
    entities = [UserEntity::class, ActiveEntity::class],
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

    companion object {
        const val SEPARATOR = ";"
    }
}