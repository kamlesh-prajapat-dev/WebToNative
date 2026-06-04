package com.example.webtonative.util.logger

import jakarta.inject.Inject
import jakarta.inject.Singleton
import timber.log.Timber

@Singleton
class AppLogger @Inject constructor(
    private val timber: Timber.Forest
) : Logger {
    override fun e(
        tag: String,
        message: String,
        t: Throwable?
    ) {
        timber.tag(tag).e(
            t = t,
            message = message
        )
    }
}