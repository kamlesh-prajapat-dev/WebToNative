package com.example.webtonative.auth.di

import com.example.webtonative.auth.data.repository.AuthRepositoryImpl
import com.example.webtonative.auth.domain.repository.AuthRepository
import com.example.webtonative.core.di.qualifier.LoginRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @LoginRepo
    @Binds
    @Singleton
    abstract fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}