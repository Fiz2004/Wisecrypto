package com.fiz.wisecrypto.data.repositories

import com.fiz.wisecrypto.domain.models.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(val dispatcher: CoroutineDispatcher = Dispatchers.Default) {
    suspend fun saveUser(
        fullName: String,
        numberPhone: String,
        userName: String,
        email: String,
        password: String
    ): Boolean {
        return withContext(dispatcher) {

            true
        }
    }

    suspend fun checkUser(
        email: String,
        password: String
    ): User? {
        return withContext(dispatcher) {

            User(email = email, fullName = "Test Test", numberPhone = "128923", userName = "Test")
        }
    }
}