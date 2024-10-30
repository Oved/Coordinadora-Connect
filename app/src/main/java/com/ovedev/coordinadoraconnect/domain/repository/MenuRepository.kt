package com.ovedev.coordinadoraconnect.domain.repository

import com.ovedev.coordinadoraconnect.data.remote.response.PdfResponse

interface MenuRepository {

    suspend fun getPdfImage(): PdfResponse

}