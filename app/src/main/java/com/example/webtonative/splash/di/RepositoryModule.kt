package com.example.webtonative.splash.di

import com.example.webtonative.core.di.qualifier.SplashRepo
import com.example.webtonative.splash.data.repository.AuthRepositoryImpl
import com.example.webtonative.splash.domain.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @SplashRepo
    @Binds
    @Singleton
    abstract fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}