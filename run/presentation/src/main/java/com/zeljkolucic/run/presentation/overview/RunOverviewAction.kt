package com.zeljkolucic.run.presentation.overview

sealed interface RunOverviewAction {
    data object OnAnalyticsClick: RunOverviewAction
    data object OnLogoutClick: RunOverviewAction
    data object OnStartClick: RunOverviewAction
}