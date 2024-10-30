package com.ovedev.coordinadoraconnect.di

import com.ovedev.coordinadoraconnect.data.repository.AuthRepositoryImpl
import com.ovedev.coordinadoraconnect.data.repository.FirebaseRepositoryImpl
import com.ovedev.coordinadoraconnect.data.repository.MenuRepositoryImpl
import com.ovedev.coordinadoraconnect.data.repository.UserDBRepositoryImpl
import com.ovedev.coordinadoraconnect.domain.repository.AuthRepository
import com.ovedev.coordinadoraconnect.domain.repository.FirebaseRepository
import com.ovedev.coordinadoraconnect.domain.repository.MenuRepository
import com.ovedev.coordinadoraconnect.domain.repository.UserDBRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun provideAuthRepository(repository: AuthRepositoryImpl): AuthRepository

    @Binds
    fun provideMenuRepository(repository: MenuRepositoryImpl): MenuRepository

    @Binds
    fun provideFirebaseRepository(repository: FirebaseRepositoryImpl): FirebaseRepository

    @Binds
    fun provideUserDBRepository(repository: UserDBRepositoryImpl): UserDBRepository

}