package com.ovedev.coordinadoraconnect.presentation.model

import com.ovedev.coordinadoraconnect.data.local.entity.UserEntity

data class UserModel(
    val userId: String,
    val username: String,
    val registerDate: String,
    val validationPeriod: Int
)

fun UserModel.toEntity(): UserEntity {
    return UserEntity(
        userId = this.userId,
        userName = this.username,
        registerDate = this.registerDate,
        validationPeriod = this.validationPeriod
    )
}
