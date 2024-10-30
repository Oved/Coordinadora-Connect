package com.ovedev.coordinadoraconnect.di

import android.content.Context
import com.ovedev.coordinadoraconnect.CoordinadoraConnectApp
import com.ovedev.coordinadoraconnect.utils.FileUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GlobalModule {

    @Provides
    fun provideFileUtil(@ApplicationContext appContext: Context): FileUtil {
        return FileUtil(appContext as CoordinadoraConnectApp)
    }

}