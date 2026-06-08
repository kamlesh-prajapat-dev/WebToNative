package com.example.webtonative.notification.schedular

import androidx.work.BackoffPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.webtonative.notification.worker.NotificationWorker
import com.example.webtonative.util.logger.Logger
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationSchedular @Inject constructor(
    private val workManager: WorkManager,
    private val logger: Logger
) {
    private companion object {
        private const val UNIQUE_WORK_NAME = "after_login_welcome_notification_work"
    }

    fun scheduleWelcomeNotification() {

        logger.e("NotificationWorker", message = "Schedule Notification Worker.")

        val request = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                10, TimeUnit.MINUTES
            )
            .build()

        workManager.enqueueUniqueWork(
            UNIQUE_WORK_NAME,
            ExistingWorkPolicy.KEEP,
            request = request
        )
    }
}