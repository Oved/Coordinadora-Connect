package com.ovedev.coordinadoraconnect.data.repository

import com.ovedev.coordinadoraconnect.data.remote.datasource.AuthDataSource
import com.ovedev.coordinadoraconnect.data.remote.response.LoginResponse
import com.ovedev.coordinadoraconnect.domain.repository.AuthRepository
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

}