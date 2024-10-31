package com.ovedev.coordinadoraconnect.domain.usecase

import com.ovedev.coordinadoraconnect.data.Response
import com.ovedev.coordinadoraconnect.domain.repository.FirebaseRepository
import com.ovedev.coordinadoraconnect.domain.repository.UserDBRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CloseSessionUseCase @Inject constructor(
    private val userDBRepository: UserDBRepository,
    private val firebaseRepository: FirebaseRepository
) {
    operator fun invoke(): Flow<Response<Boolean>> = flow {
        emit(Response.Loading(true))
        try {
            val user = userDBRepository.getUser()
            user?.let {
                userDBRepository.deleteUser(it)
                firebaseRepository.deleteUser(it.userId)
            }
            emit(Response.Success(true))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Response.Error(e.message ?: "Error desconocido"))
        }
        emit(Response.Loading(false))
    }
}