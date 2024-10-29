package com.ovedev.coordinadoraconnect.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ovedev.coordinadoraconnect.presentation.model.UserModel

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val username: String,
    val password: String,
    val validationPeriod: Int
)

fun UserEntity.toDomain(): UserModel {
    return UserModel(
        id = this.id,
        username = this.username,
        password = this.password,
        validationPeriod = this.validationPeriod
    )
}