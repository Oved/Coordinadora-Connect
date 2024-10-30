package com.ovedev.coordinadoraconnect.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ovedev.coordinadoraconnect.presentation.model.UserModel

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val userId: String,
    val userName: String,
    val registerDate: String,
    val validationPeriod: Int
)

fun UserEntity.toDomain(): UserModel {
    return UserModel(
        userId = this.userId,
        username = this.userName,
        registerDate = this.registerDate,
        validationPeriod = this.validationPeriod
    )
}