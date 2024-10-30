package com.ovedev.coordinadoraconnect.domain.repository

import com.ovedev.coordinadoraconnect.data.remote.response.LoginResponse

interface AuthRepository {

    suspend fun login(username: String, password: String): LoginResponse

}