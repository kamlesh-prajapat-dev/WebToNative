package com.example.webtonative.notification.schedular

import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.webtonative.notification.worker.NotificationWorker
import com.example.webtonative.util.logger.Logger
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DailyNotificationSchedular @Inject constructor(
    private val workManager: WorkManager,
    private val logger: Logger
) {
    private companion object {
        private const val UNIQUE_WORK_NAME = "daily_welcome_notification_work"
    }

    fun scheduleDailyWelcomeNotification() {

        logger.e("DailyNotificationSchedular", message = "Schedule Notification Worker.")

        val periodicRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
            24, TimeUnit.HOURS,
            15, TimeUnit.MINUTES
        )
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                10, TimeUnit.MINUTES
            )
            .build()

        workManager.enqueueUniquePeriodicWork(
            UNIQUE_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicRequest
        )
    }

    fun cancelSchedular() {
        workManager.cancelUniqueWork(UNIQUE_WORK_NAME)
    }
}