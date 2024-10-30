package com.ovedev.coordinadoraconnect.domain.repository

import com.ovedev.coordinadoraconnect.data.remote.response.LoginResponse
import com.ovedev.coordinadoraconnect.presentation.model.UserModel

interface AuthRepository {

    suspend fun login(username: String, password: String): LoginResponse

    suspend fun saveUser(user: UserModel)

    suspend fun getUser(): UserModel?

}