package com.zeljkolucic.run.presentation.di

import com.zeljkolucic.run.domain.RunningTracker
import com.zeljkolucic.run.presentation.active_run.ActiveRunViewModel
import com.zeljkolucic.run.presentation.overview.RunOverviewViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val runPresentationModule = module {
    singleOf(::RunningTracker)

    viewModelOf(::RunOverviewViewModel)
    viewModelOf(::ActiveRunViewModel)
}