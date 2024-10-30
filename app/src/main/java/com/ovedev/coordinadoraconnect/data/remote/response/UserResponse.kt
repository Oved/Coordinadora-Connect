package com.ovedev.coordinadoraconnect.data.remote.response

import com.google.firebase.Timestamp

data class UserResponse(
    val userId: String?,
    val userName: String,
    val validationPeriod: Int,
    val registerDate: Timestamp,
)
