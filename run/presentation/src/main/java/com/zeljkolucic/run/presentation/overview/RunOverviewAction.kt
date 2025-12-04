package com.zeljkolucic.run.presentation.overview

import com.zeljkolucic.run.presentation.overview.model.RunUi

sealed interface RunOverviewAction {
    data object OnAnalyticsClick: RunOverviewAction
    data object OnLogoutClick: RunOverviewAction
    data object OnStartClick: RunOverviewAction
    data class DeleteRun(val runUi: RunUi): RunOverviewAction
}