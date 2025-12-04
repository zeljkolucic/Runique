package com.zeljkolucic.run.presentation.overview

import com.zeljkolucic.run.presentation.overview.model.RunUi

data class RunOverviewState(
    val runs: List<RunUi> = emptyList()
)
