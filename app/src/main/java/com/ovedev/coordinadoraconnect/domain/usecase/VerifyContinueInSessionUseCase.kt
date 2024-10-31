package com.ovedev.coordinadoraconnect.domain.usecase

import com.ovedev.coordinadoraconnect.data.Response
import com.ovedev.coordinadoraconnect.data.local.entity.toDomain
import com.ovedev.coordinadoraconnect.domain.repository.FirebaseRepository
import com.ovedev.coordinadoraconnect.domain.repository.UserDBRepository
import com.ovedev.coordinadoraconnect.presentation.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VerifyContinueInSessionUseCase @Inject constructor(
    private val userDBRepository: UserDBRepository,
    private val firebaseRepository: FirebaseRepository
) {
    operator fun invoke(): Flow<Response<UserModel?>> = flow {
        emit(Response.Loading(true))
        try {
            val user = userDBRepository.getUser()
            if (user == null || user.validationPeriod <= 0) {
                emit(Response.Success(null))
                return@flow
            }
            userDBRepository.updateUserEntity(user.copy(validationPeriod = user.validationPeriod - 1))
            firebaseRepository.updateValidationPeriod(user.userId, user.validationPeriod - 1)
            emit(Response.Success(user.toDomain()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Response.Error(e.message ?: "Error desconocido"))
        }
        emit(Response.Loading(false))
    }
}