package com.ovedev.coordinadoraconnect.data.remote.response

data class LoginResponse(
    val isError: Boolean? = null,
    val message: String? = null,
    val dataUserName: String? = null,
    val validationPeriod: Int? = null
)
