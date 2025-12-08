package com.zeljkolucic.run.data.di

import com.zeljkolucic.core.domain.run.SyncRunScheduler
import com.zeljkolucic.run.data.CreateRunWorker
import com.zeljkolucic.run.data.DeleteRunWorker
import com.zeljkolucic.run.data.FetchRunsWorker
import com.zeljkolucic.run.data.SyncRunWorkerScheduler
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val runDataModule = module {
    workerOf(::CreateRunWorker)
    workerOf(::FetchRunsWorker)
    workerOf(::DeleteRunWorker)

    singleOf(::SyncRunWorkerScheduler).bind<SyncRunScheduler>()
}