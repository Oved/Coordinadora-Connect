package com.ovedev.coordinadoraconnect.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ovedev.coordinadoraconnect.data.local.dao.UserDao
import com.ovedev.coordinadoraconnect.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}