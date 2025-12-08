package com.zeljkolucic.analytics.data.di

import com.zeljkolucic.analytics.data.RoomAnalyticsRepository
import com.zeljkolucic.analytics.domain.AnalyticsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val analyticsModule = module {
    singleOf(::RoomAnalyticsRepository).bind<AnalyticsRepository>()
}