package com.ovedev.coordinadoraconnect.domain.usecase

import com.ovedev.coordinadoraconnect.data.Response
import com.ovedev.coordinadoraconnect.data.remote.response.LoginResponse
import com.ovedev.coordinadoraconnect.domain.repository.AuthRepository
import com.ovedev.coordinadoraconnect.domain.repository.FirebaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val firebaseRepository: FirebaseRepository
) {
    operator fun invoke(username: String, password: String): Flow<Response<LoginResponse>> = flow {
        emit(Response.Loading(true))
        try {
            val response = authRepository.login(username, password)
            if (response.isError == true) {
                emit(Response.Error(response.message ?: "Error desconocido"))
            } else {
                val user = firebaseRepository.getUser(username)

                if (user == null) emit(Response.Error(response.message ?: "Error en la consulta"))
                else if (user.userId == null) {
                    val result = firebaseRepository.saveUser(userId = username, userName = "Oved Rincón", validationPeriod = response.validationPeriod ?: 0)
                    if (result) emit(Response.Success(response))
                    else emit(Response.Error(response.message ?: "No se guardó el usuario"))
                } else {
                    val resultUpdate = firebaseRepository.updateValidationPeriod(user.userId, user.validationPeriod - 1) //TODO validar estado
                    if (resultUpdate) emit(Response.Success(response))
                    else emit(Response.Error("Error en la petición"))
                }
            }
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "Error desconocido"))
        }
        emit(Response.Loading(false))
    }
}