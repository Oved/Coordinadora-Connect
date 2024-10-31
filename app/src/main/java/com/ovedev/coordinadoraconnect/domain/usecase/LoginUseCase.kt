package com.ovedev.coordinadoraconnect.domain.usecase

import com.ovedev.coordinadoraconnect.data.Response
import com.ovedev.coordinadoraconnect.data.local.entity.UserEntity
import com.ovedev.coordinadoraconnect.data.remote.response.LoginResponse
import com.ovedev.coordinadoraconnect.domain.repository.AuthRepository
import com.ovedev.coordinadoraconnect.domain.repository.FirebaseRepository
import com.ovedev.coordinadoraconnect.domain.repository.UserDBRepository
import com.ovedev.coordinadoraconnect.utils.dateToString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userDBRepository: UserDBRepository,
    private val firebaseRepository: FirebaseRepository
) {
    operator fun invoke(username: String, password: String): Flow<Response<LoginResponse>> = flow {
        emit(Response.Loading(true))
        try {
            val response = authRepository.login(username, password)
            if (response.isError == true) {
                emit(Response.Error(response.message ?: "Error desconocido"))
            } else {
                val userNameResponse = response.dataUserName.toString()
                userDBRepository.saveUser(createUserEntity(userId = username, response.validationPeriod, userNameResponse))
                val userSaved = firebaseRepository.saveUser(userId = username, userName = userNameResponse, validationPeriod = response.validationPeriod ?: 0)
                if (userSaved) emit(Response.Success(response))
                else emit(Response.Error(response.message ?: "Error en el servicio"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Response.Error(e.message ?: "Error desconocido"))
        }
        emit(Response.Loading(false))
    }

    private fun createUserEntity(userId: String, period: Int?, dataUserName: String) = UserEntity(
        userId = userId,
        userName = dataUserName,
        registerDate = Date().dateToString(),
        validationPeriod = period ?: 0
    )
}