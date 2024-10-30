package com.ovedev.coordinadoraconnect.domain.usecase

import com.ovedev.coordinadoraconnect.data.Response
import com.ovedev.coordinadoraconnect.data.remote.response.LoginResponse
import com.ovedev.coordinadoraconnect.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(username: String, password: String): Flow<Response<LoginResponse>> = flow {
        emit(Response.Loading(true))
        try {
            val response = authRepository.login(username, password)
            if (response.isError == true) {
                emit(Response.Error(response.message ?: "Error desconocido"))
            } else {
                emit(Response.Success(response))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "Error desconocido"))
        }
        emit(Response.Loading(false))
    }
}