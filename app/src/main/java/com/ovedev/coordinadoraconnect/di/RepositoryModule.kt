package com.ovedev.coordinadoraconnect.di

import com.ovedev.coordinadoraconnect.data.repository.AuthRepositoryImpl
import com.ovedev.coordinadoraconnect.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun provideAuthRepository(repository: AuthRepositoryImpl): AuthRepository

}