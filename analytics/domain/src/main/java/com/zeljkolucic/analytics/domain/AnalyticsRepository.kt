package com.zeljkolucic.analytics.domain

interface AnalyticsRepository {
    suspend fun getAnalyticsValues(): AnalyticsValues
}