package com.ovedev.coordinadoraconnect.data.repository

import com.ovedev.coordinadoraconnect.domain.repository.AuthRepository
import com.ovedev.coordinadoraconnect.presentation.model.UserModel
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(

): AuthRepository {

    override fun login(username: String, password: String, onSuccess: (UserModel) -> Unit, onError: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun saveUser(user: UserModel) {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(): UserModel? {
        TODO("Not yet implemented")
    }

}