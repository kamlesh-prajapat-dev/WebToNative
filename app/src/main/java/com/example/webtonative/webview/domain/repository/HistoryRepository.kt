package com.example.webtonative.webview.domain.repository

import com.example.webtonative.core.domain.util.Result
import com.example.webtonative.core.domain.util.error.LocalDBError
import com.example.webtonative.webview.domain.model.HistoryGroup
import com.example.webtonative.webview.domain.model.HistoryItem
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    fun getHistory(): Flow<List<HistoryGroup>>

    suspend fun saveHistory(item: HistoryItem): Result<Boolean, LocalDBError>

    suspend fun deleteAllHistory(): Result<Boolean, LocalDBError>
}