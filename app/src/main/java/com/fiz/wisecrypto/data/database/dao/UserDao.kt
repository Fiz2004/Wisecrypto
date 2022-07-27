package com.fiz.wisecrypto.data.database.dao

import androidx.room.*
import com.fiz.wisecrypto.data.entity.ActiveEntity
import com.fiz.wisecrypto.data.entity.TransactionEntity
import com.fiz.wisecrypto.data.entity.UserEntity
import com.fiz.wisecrypto.domain.models.transaction.StatusTransaction
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    fun getAll(): List<UserEntity>

    @Query("SELECT * FROM UserEntity WHERE email =:email")
    suspend fun getUserByEmail(email: String): UserEntity

    @Query("SELECT * FROM ActiveEntity WHERE emailId =:emailId")
    suspend fun getActives(emailId: String): List<ActiveEntity>

    @Query("SELECT * FROM TransactionEntity WHERE emailId =:emailId")
    suspend fun getTransactions(emailId: String): List<TransactionEntity>

    @Query("SELECT * FROM UserEntity WHERE email =:email")
    fun observeUserByEmail(email: String): Flow<UserEntity>

    @Transaction
    suspend fun saveUser(userEntity: UserEntity) {
        insert(userEntity)
        insertActives(userEntity.actives)
        insertTransactions(userEntity.transactions)
    }

    @Query("SELECT EXISTS (SELECT* FROM UserEntity WHERE (email =:email AND password =:password))")
    suspend fun isValidateEmailPassword(email: String, password: String): Boolean

    @Query("SELECT EXISTS (SELECT* FROM UserEntity WHERE numberPhone =:numberPhone)")
    suspend fun isValidatePhone(numberPhone: String): Boolean

    @Query("UPDATE UserEntity SET password=:password WHERE email =:email")
    suspend fun savePassword(email: String, password: String)

    @Query("UPDATE ActiveEntity SET count=:count WHERE id =:id")
    suspend fun saveActives(id: String, count: Double)

    @Query("UPDATE UserEntity SET balance=:balance WHERE email =:email")
    suspend fun saveBalance(email: String, balance: Long)

    @Query("UPDATE UserEntity SET watchList=:watchList WHERE email =:email")
    suspend fun saveWatchList(email: String, watchList: List<String>)

    @Transaction
    suspend fun saveActivesAndSaveBalance(
        email: String,
        actives: List<ActiveEntity>,
        newBalance: Long,
        transactionEntity: TransactionEntity
    ) {
        deleteActives()
        insertActives(actives)
        saveBalance(email, newBalance, transactionEntity)
    }

    @Transaction
    suspend fun saveBalance(
        email: String,
        newBalance: Long,
        transactionEntity: TransactionEntity
    ) {
        insertTransactions(listOf(transactionEntity))
        saveBalance(email, newBalance)
        update(transactionEntity.copy(status = StatusTransaction.Success))
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(userEntity: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActives(actives: List<ActiveEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(transactions: List<TransactionEntity>)

    @Update
    suspend fun update(userEntity: UserEntity)

    @Update
    suspend fun update(transactionEntity: TransactionEntity)

    @Update
    suspend fun updateAll(userEntity: List<UserEntity>)

    @Delete
    suspend fun delete(userEntity: UserEntity)

    @Query("DELETE FROM ActiveEntity")
    suspend fun deleteActives()

    @Query("DELETE FROM TransactionEntity")
    suspend fun deleteTransactions()

    @Query("DELETE FROM UserEntity")
    suspend fun deleteAll()
}