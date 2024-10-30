package com.ovedev.coordinadoraconnect.data.repository

import com.ovedev.coordinadoraconnect.data.remote.datasource.AuthDataSource
import com.ovedev.coordinadoraconnect.data.remote.response.PdfResponse
import com.ovedev.coordinadoraconnect.domain.repository.MenuRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class MenuRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
) : MenuRepository {

    override suspend fun getPdfImage(): PdfResponse = suspendCancellableCoroutine { cont ->
        authDataSource.getPdfImage { response ->
            cont.resume(response)
        }
    }

}