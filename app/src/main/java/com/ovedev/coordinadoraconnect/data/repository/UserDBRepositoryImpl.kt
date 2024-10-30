package com.ovedev.coordinadoraconnect.data.repository

import com.ovedev.coordinadoraconnect.data.local.dao.UserDao
import com.ovedev.coordinadoraconnect.data.local.entity.UserEntity
import com.ovedev.coordinadoraconnect.domain.repository.UserDBRepository
import javax.inject.Inject

class UserDBRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserDBRepository {

    override suspend fun saveUser(userEntity: UserEntity) {
        userDao.insertUser(userEntity)
    }

    override suspend fun getUser() = userDao.getAllUsers().firstOrNull()


    override suspend fun updateUserEntity(userEntity: UserEntity) {
        userDao.update(userEntity)
    }

    override suspend fun deleteUser(userEntity: UserEntity) {
        userDao.delete(userEntity)
    }

}