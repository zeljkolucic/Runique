package com.zeljkolucic.run.data.di

import com.zeljkolucic.run.data.CreateRunWorker
import com.zeljkolucic.run.data.DeleteRunWorker
import com.zeljkolucic.run.data.FetchRunsWorker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.dsl.module

val runDataModule = module {
    workerOf(::CreateRunWorker)
    workerOf(::FetchRunsWorker)
    workerOf(::DeleteRunWorker)
}