package com.example.webtonative.core.di.module

import com.example.webtonative.util.logger.AppLogger
import com.example.webtonative.util.logger.Logger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LoggerModule {

    @Binds
    @Singleton
    abstract fun provideLogger(imp: AppLogger): Logger
}