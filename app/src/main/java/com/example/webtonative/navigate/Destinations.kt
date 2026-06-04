package com.example.webtonative.navigate

import kotlinx.serialization.Serializable

@Serializable
object Splash

@Serializable
object Login

@Serializable
object Home

@Serializable
object History

@Serializable
data class WebView(
    val url: String
)