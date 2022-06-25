package com.fiz.wisecrypto.data.database.dao

import androidx.room.*
import com.fiz.wisecrypto.data.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    fun getAll(): List<UserEntity>

    @Query("SELECT * FROM UserEntity WHERE email =:email")
    suspend fun getUserByEmail(email: String): UserEntity

    @Query("SELECT EXISTS (SELECT* FROM UserEntity WHERE (email =:email AND password =:password))")
    suspend fun isValidateEmailPassword(email: String, password: String): Boolean

    @Query("SELECT EXISTS (SELECT* FROM UserEntity WHERE numberPhone =:numberPhone)")
    suspend fun isValidatePhone(numberPhone: String): Boolean

    @Query("UPDATE UserEntity SET password=:password WHERE numberPhone =:numberPhone")
    suspend fun changePassword(numberPhone: String, password: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(userEntity: List<UserEntity>)

    @Update
    suspend fun update(userEntity: UserEntity)

    @Update
    suspend fun updateAll(userEntity: List<UserEntity>)

    @Delete
    suspend fun delete(userEntity: UserEntity)

    @Query("DELETE FROM UserEntity")
    suspend fun deleteAll()
}