package com.example.webtonative.webview.domain.model

data class HistoryItem(
    val id: Int,
    val url: String,
    val title: String,
    val visitCount: Int,
    val lastVisitTime: String,
    val iconInitial: Char
)