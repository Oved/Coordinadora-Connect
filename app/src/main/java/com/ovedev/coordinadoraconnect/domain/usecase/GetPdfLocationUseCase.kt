package com.ovedev.coordinadoraconnect.domain.usecase

import com.ovedev.coordinadoraconnect.data.Response
import com.ovedev.coordinadoraconnect.data.remote.response.PdfResponse
import com.ovedev.coordinadoraconnect.domain.repository.MenuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPdfLocationUseCase @Inject constructor(
    private val menuRepository: MenuRepository
) {
    operator fun invoke() : Flow<Response<PdfResponse>> = flow {
        emit(Response.Loading(true))
        try {
            val response = menuRepository.getPdfImage()
            if (response.isError == true) {
                emit(Response.Error(""))
            } else {
                emit(Response.Success(response))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "Error desconocido"))
        }
        emit(Response.Loading(false))
    }

}