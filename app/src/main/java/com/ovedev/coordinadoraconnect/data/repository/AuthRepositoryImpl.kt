package com.ovedev.coordinadoraconnect.data.repository

import com.ovedev.coordinadoraconnect.data.remote.AuthDataSource
import com.ovedev.coordinadoraconnect.data.remote.response.LoginResponse
import com.ovedev.coordinadoraconnect.domain.repository.AuthRepository
import com.ovedev.coordinadoraconnect.presentation.model.UserModel
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
) : AuthRepository {

    override suspend fun login(username: String, password: String): LoginResponse = suspendCancellableCoroutine { cont ->
        authDataSource.login(username, password) { response ->
            cont.resume(response)
        }
    }

    override suspend fun saveUser(user: UserModel) {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(): UserModel? {
        TODO("Not yet implemented")
    }

}