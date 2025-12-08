package com.zeljkolucic.analytics.data

import com.zeljkolucic.analytics.domain.AnalyticsRepository
import com.zeljkolucic.analytics.domain.AnalyticsValues
import com.zeljkolucic.core.database.dao.AnalyticsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

class RoomAnalyticsRepository(
    private val analyticsDao: AnalyticsDao
): AnalyticsRepository {
    override suspend fun getAnalyticsValues(): AnalyticsValues {
        return withContext(Dispatchers.IO) {
            val totalDistance = async { analyticsDao.getTotalDistance() }
            val totalTimeMillis = async { analyticsDao.getTotalTimeRun() }
            val maxRunSpeed = async { analyticsDao.getMaxRunSpeed() }
            val avgDistancePerRun = async { analyticsDao.getAverageDistancePerRun() }
            val avgPacePerRun = async { analyticsDao.getAveragePacePerRun() }

            AnalyticsValues(
                totalDistanceRun = totalDistance.await(),
                totalTimeRun = totalTimeMillis.await().milliseconds,
                fastestRunEver = maxRunSpeed.await(),
                avgDistancePerRun = avgDistancePerRun.await(),
                avgPacePerRun = avgPacePerRun.await()
            )
        }
    }
}