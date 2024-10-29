package com.ovedev.coordinadoraconnect.data.remote.response

import com.ovedev.coordinadoraconnect.presentation.model.Position

data class PdfResponse(
    val isError: Boolean? = null,
    val base64Data: String? = null,
    val positions: List<Position>? = null
)
