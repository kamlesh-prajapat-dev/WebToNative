package com.example.webtonative.webview.data.repository

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteFullException
import com.example.webtonative.core.domain.util.Result
import com.example.webtonative.core.domain.util.error.LocalDBError
import com.example.webtonative.util.logger.Logger
import com.example.webtonative.webview.data.local.dao.HistoryDao
import com.example.webtonative.webview.domain.model.HistoryGroup
import com.example.webtonative.webview.domain.model.HistoryItem
import com.example.webtonative.webview.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.cancellation.CancellationException
import com.example.webtonative.webview.data.local.entity.HistoryItem as HistoryEntity

@Singleton
class HistoryRepositoryImpl @Inject constructor(
    private val localDB: HistoryDao,
    private val logger: Logger
) : HistoryRepository {

    private companion object {
        const val TAG = "HistoryRepositoryImpl"
    }

    override fun getHistory(): Flow<List<HistoryGroup>> {
        return localDB.getHistory()
            .map { entities ->
                entities?.toHistoryGroups() ?: emptyList()
            }
            .catch { e ->
                logger.e(TAG, t = e, message = "Flow Error: ${e.message}")
                if (e is CancellationException) throw e
            }
    }

    override suspend fun saveHistory(
        item: HistoryItem
    ): Result<Boolean, LocalDBError> {
        return try {
            val rowsUpdated = localDB.updateHistoryItem(item.url, System.currentTimeMillis())

            if (rowsUpdated == 0) {
                localDB.insertHistoryItem(item.toEntity())
            }

            Result.Success<Boolean, LocalDBError>(true)
        } catch (e: CancellationException) {
            throw e
        } catch (e: SQLiteConstraintException) {
            logger.e(
                tag = TAG,
                t = e,
                message = e.message ?: "Constraint Violation Error: ${e.cause}"
            )
            Result.Error(LocalDBError.RoomError.ConstraintViolation)
        } catch (e: SQLiteFullException) {
            logger.e(
                tag = TAG,
                t = e,
                message = e.message ?: "Constraint Violation Error: ${e.cause}"
            )
            Result.Error(LocalDBError.RoomError.DiskFull)
        } catch (e: SQLiteException) {
            logger.e(
                tag = TAG,
                t = e,
                message = e.message ?: "Constraint Violation Error: ${e.cause}"
            )
            Result.Error(LocalDBError.RoomError.Unknown)
        } catch (e: Exception) {
            logger.e(
                tag = TAG,
                t = e,
                message = e.message ?: "IOException: ${e.cause}"
            )
            Result.Error(LocalDBError.RoomError.Unknown)
        }
    }

    override suspend fun deleteAllHistory(): Result<Boolean, LocalDBError> {
        return try {
            localDB.deleteAllHistory()
            Result.Success<Boolean, LocalDBError>(true)
        }  catch (e: CancellationException) {
            throw e
        } catch (e: SQLiteConstraintException) {
            logger.e(
                tag = TAG,
                t = e,
                message = e.message ?: "Constraint Violation Error: ${e.cause}"
            )
            Result.Error(LocalDBError.RoomError.ConstraintViolation)
        } catch (e: SQLiteFullException) {
            logger.e(
                tag = TAG,
                t = e,
                message = e.message ?: "Constraint Violation Error: ${e.cause}"
            )
            Result.Error(LocalDBError.RoomError.DiskFull)
        } catch (e: SQLiteException) {
            logger.e(
                tag = TAG,
                t = e,
                message = e.message ?: "Constraint Violation Error: ${e.cause}"
            )
            Result.Error(LocalDBError.RoomError.Unknown)
        } catch (e: Exception) {
            logger.e(
                tag = TAG,
                t = e,
                message = e.message ?: "IOException: ${e.cause}"
            )
            Result.Error(LocalDBError.RoomError.Unknown)
        }
    }

    private fun HistoryEntity.toDomain(): HistoryItem {
        return HistoryItem(
            id = id,
            url = url,
            title = title,
            visitCount = visitCount,
            lastVisitTime = lastVisitTime.toHistoryTimeLabel(),
            iconInitial = iconInitial[0]
        )
    }

    private fun HistoryItem.toEntity(): HistoryEntity {
        return HistoryEntity(
            id = id,
            url = url,
            title = title,
            visitCount = visitCount,
            lastVisitTime = System.currentTimeMillis(),
            iconInitial = iconInitial.toString()
        )
    }

    private fun Long.toHistoryTimeLabel(): String {

        val now = Instant.now()
        val instant = Instant.ofEpochMilli(this)

        val duration = Duration.between(instant, now)

        val zoneId = ZoneId.systemDefault()

        val dateTime = instant.atZone(zoneId)
        val today = LocalDate.now(zoneId)
        val date = dateTime.toLocalDate()

        return when (date) {
            today -> {
                when {
                    duration.toMinutes() < 1 ->
                        "Just now"

                    duration.toHours() < 1 ->
                        "${duration.toMinutes()}m ago"

                    else ->
                        "${duration.toHours()}h ago"
                }
            }

            // Yesterday
            today.minusDays(1) -> {
                val formatter =
                    DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault())

                "Yesterday ${dateTime.format(formatter)}"
            }

            // Older
            else -> {
                val formatter =
                    DateTimeFormatter.ofPattern("MMM d", Locale.getDefault())

                dateTime.format(formatter)
            }
        }
    }

    fun List<HistoryEntity>.toHistoryGroups(): List<HistoryGroup> {

        val today = LocalDate.now()
        val yesterday = today.minusDays(1)

        return groupBy { item ->

            val date = Instant.ofEpochMilli(item.lastVisitTime)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            when (date) {
                today -> "Today"
                yesterday -> "Yesterday"
                else -> "Older"
            }
        }.map { (header, items) ->
            HistoryGroup(header, items.map { it.toDomain() })
        }
    }
}
