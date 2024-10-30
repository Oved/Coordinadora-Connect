package com.ovedev.coordinadoraconnect.domain.repository

import com.ovedev.coordinadoraconnect.data.remote.response.UserResponse

interface FirebaseRepository {

    suspend fun saveUser(userId: String, userName: String, validationPeriod: Int): Boolean

    suspend fun getUser(userId: String): UserResponse?

    suspend fun updateValidationPeriod(userId: String, newValidationPeriod: Int): Boolean

    suspend fun deleteUser(userId: String): Boolean

}