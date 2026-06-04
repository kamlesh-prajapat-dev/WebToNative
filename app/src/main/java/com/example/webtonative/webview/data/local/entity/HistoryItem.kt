package com.example.webtonative.webview.data.local.entity

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "history",
    indices = [
        Index(value = ["lastVisitTime"])
    ]
)
data class HistoryItem(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val url: String,
    val title: String,
    val visitCount: Int,
    val lastVisitTime: Long,
    val iconInitial: String
)
