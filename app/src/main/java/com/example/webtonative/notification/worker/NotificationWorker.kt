package com.example.webtonative.notification.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.webtonative.notification.helper.NotificationHelper
import com.example.webtonative.util.logger.Logger
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val logger: Logger
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        logger.e("NotificationWorker", message = "Execute Notification Worker.")

        if (ProcessLifecycleOwner.get()
                .lifecycle.currentState
                .isAtLeast(Lifecycle.State.STARTED)
        ) {
            return Result.retry()
        }

        NotificationHelper.showNotification(applicationContext)
        return Result.success()
    }
}