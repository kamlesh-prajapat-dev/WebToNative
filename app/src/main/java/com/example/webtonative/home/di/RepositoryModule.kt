package com.example.webtonative.home.di

import com.example.webtonative.core.di.qualifier.HomeRepo
import com.example.webtonative.home.data.repository.AuthRepositoryImpl
import com.example.webtonative.home.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @HomeRepo
    @Binds
    @Singleton
    abstract fun provideHomeAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository
}