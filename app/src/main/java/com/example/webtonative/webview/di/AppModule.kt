package com.example.webtonative.webview.di

import android.content.Context
import androidx.room.Room
import com.example.webtonative.webview.data.local.dao.HistoryDao
import com.example.webtonative.webview.data.local.db.LocalDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val DATABASE_NAME = "web_to_native_database"

    @Provides
    @Singleton
    fun provideLocalDB(
        @ApplicationContext context: Context
    ): LocalDB {
        return Room.databaseBuilder(
            context = context,
            klass = LocalDB::class.java,
            name = DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideHistoryDao(
        db: LocalDB
    ): HistoryDao {
        return db.historyDao()
    }
}