package com.ovedev.coordinadoraconnect.di

import android.content.Context
import androidx.room.Room
import com.ovedev.coordinadoraconnect.data.local.dao.UserDao
import com.ovedev.coordinadoraconnect.data.local.db.UsersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): UsersDatabase {
        return Room.databaseBuilder(
            context,
            UsersDatabase::class.java,
            "users_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideUserDao(database: UsersDatabase): UserDao {
        return database.userDao()
    }
}
