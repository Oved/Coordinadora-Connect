package com.ovedev.coordinadoraconnect.domain.repository

import com.ovedev.coordinadoraconnect.presentation.model.UserModel

interface AuthRepository {

    fun login(username: String, password: String, onSuccess: (UserModel) -> Unit, onError: (String) -> Unit)

    suspend fun saveUser(user: UserModel)

    suspend fun getUser(): UserModel?

}