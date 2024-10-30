package com.ovedev.coordinadoraconnect.domain.usecase

import com.ovedev.coordinadoraconnect.data.Response
import com.ovedev.coordinadoraconnect.domain.repository.UserDBRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VerifyIsSessionActive @Inject constructor(
    private val userDBRepository: UserDBRepository
) {
    operator fun invoke(): Flow<Response<Boolean>> = flow {
        emit(Response.Loading(true))
        try {
            val isSessionActive = userDBRepository.getUser() != null
            emit(Response.Success(isSessionActive))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "Error desconocido"))
        }
        emit(Response.Loading(false))
    }
}