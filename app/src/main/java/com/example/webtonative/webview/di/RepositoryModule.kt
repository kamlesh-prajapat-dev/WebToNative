package com.example.webtonative.webview.di

import com.example.webtonative.webview.data.repository.HistoryRepositoryImpl
import com.example.webtonative.webview.domain.repository.HistoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideHistoryRepository(
        impl: HistoryRepositoryImpl
    ): HistoryRepository
}