package com.example.webtonative.webview.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.webtonative.webview.data.local.dao.HistoryDao
import com.example.webtonative.webview.data.local.entity.HistoryItem

@Database(entities = [HistoryItem::class], version = 1, exportSchema = false)
abstract class LocalDB: RoomDatabase() {

    abstract fun historyDao(): HistoryDao
}