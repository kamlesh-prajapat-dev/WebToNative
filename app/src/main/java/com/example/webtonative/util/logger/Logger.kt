package com.example.webtonative.util.logger

interface Logger {
    fun e(
        tag: String,
        message: String,
        t: Throwable? = null
    )
}