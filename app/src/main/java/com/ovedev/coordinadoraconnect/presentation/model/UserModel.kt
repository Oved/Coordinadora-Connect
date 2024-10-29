package com.ovedev.coordinadoraconnect.presentation.model

import com.ovedev.coordinadoraconnect.data.local.entity.UserEntity

data class UserModel(
    val id: Int,
    val username: String,
    val password: String,
    val validationPeriod: Int
)

fun UserModel.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        username = this.username,
        password = this.password,
        validationPeriod = this.validationPeriod
    )
}
