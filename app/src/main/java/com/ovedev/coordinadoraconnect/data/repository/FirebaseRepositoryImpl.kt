package com.ovedev.coordinadoraconnect.data.repository

import com.ovedev.coordinadoraconnect.data.remote.datasource.FirebaseDataSource
import com.ovedev.coordinadoraconnect.data.remote.response.UserResponse
import com.ovedev.coordinadoraconnect.domain.repository.FirebaseRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource,
) : FirebaseRepository {

    override suspend fun saveUser(userId: String, userName: String, validationPeriod: Int): Boolean = suspendCancellableCoroutine { cont ->
        firebaseDataSource.saveUser(userId, userName, validationPeriod) { response ->
            cont.resume(response)
        }
    }

    override suspend fun getUser(userId: String): UserResponse? = suspendCancellableCoroutine { cont ->
        firebaseDataSource.getUser(userId) { response ->
            cont.resume(response)
        }
    }

    override suspend fun updateValidationPeriod(userId: String, newValidationPeriod: Int): Boolean = suspendCancellableCoroutine { cont ->
        firebaseDataSource.updateValidationPeriod(userId, newValidationPeriod) { response ->
            cont.resume(response)
        }
    }

    override suspend fun deleteUser(userId: String): Boolean = suspendCancellableCoroutine { cont ->
        firebaseDataSource.deleteUser(userId) { response ->
            cont.resume(response)
        }
    }

}