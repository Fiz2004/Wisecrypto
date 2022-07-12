package com.fiz.wisecrypto.data.database.dao

import androidx.room.*
import com.fiz.wisecrypto.data.entity.ActiveEntity
import com.fiz.wisecrypto.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    fun getAll(): List<UserEntity>

    @Query("SELECT * FROM UserEntity WHERE email =:email")
    fun observeUserByEmail(email: String): Flow<UserEntity>

    @Query("SELECT * FROM UserEntity WHERE email =:email")
    suspend fun getUserByEmail(email: String): UserEntity

    @Query("SELECT * FROM ActiveEntity WHERE emailId =:emailId")
    suspend fun getActives(emailId: String): List<ActiveEntity>

    @Query("SELECT EXISTS (SELECT* FROM UserEntity WHERE (email =:email AND password =:password))")
    suspend fun isValidateEmailPassword(email: String, password: String): Boolean

    @Query("SELECT EXISTS (SELECT* FROM UserEntity WHERE numberPhone =:numberPhone)")
    suspend fun isValidatePhone(numberPhone: String): Boolean

    @Query("UPDATE UserEntity SET password=:password WHERE email =:email")
    suspend fun changePassword(email: String, password: String)

    @Query("UPDATE UserEntity SET watchList=:watchList WHERE email =:email")
    suspend fun changeWatchList(email: String, watchList: List<String>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(userEntity: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActives(actives: List<ActiveEntity>)

    @Update
    suspend fun update(userEntity: UserEntity)

    @Update
    suspend fun updateAll(userEntity: List<UserEntity>)

    @Delete
    suspend fun delete(userEntity: UserEntity)

    @Query("DELETE FROM UserEntity")
    suspend fun deleteAll()
}