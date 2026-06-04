package com.example.webtonative.auth.di

import com.example.webtonative.auth.data.repository.mapper.FirebaseErrorMapper
import com.example.webtonative.core.domain.util.error.mapper.ErrorMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorModule {

    @Binds
    @Singleton
    abstract fun provideFirebaseErrorMapper(
        impl: FirebaseErrorMapper
    ): ErrorMapper
}