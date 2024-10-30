package com.ovedev.coordinadoraconnect.domain.repository

import com.ovedev.coordinadoraconnect.data.local.entity.UserEntity

interface UserDBRepository {

    suspend fun saveUser(userEntity: UserEntity)

    suspend fun getUser(userId: String): UserEntity?

    suspend fun updateUserEntity(userEntity: UserEntity)

    suspend fun deleteUser(userEntity: UserEntity)

}