package com.zeljkolucic.analytics.presentation

sealed interface AnalyticsAction {
    data object OnBackClick: AnalyticsAction
}
