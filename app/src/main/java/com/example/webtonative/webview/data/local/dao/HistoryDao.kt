package com.example.webtonative.webview.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.webtonative.webview.data.local.entity.HistoryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history ORDER BY lastVisitTime DESC")
    fun getHistory(): Flow<List<HistoryItem>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoryItem(item: HistoryItem)

    @Query("DELETE FROM history")
    suspend fun deleteAllHistory()

    @Query("UPDATE history SET visitCount = visitCount + 1, lastVisitTime = :newTime WHERE url = :url")
    suspend fun updateHistoryItem(url: String, newTime: Long): Int
}