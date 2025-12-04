package com.zeljkolucic.run.network.di

import com.zeljkolucic.core.domain.run.RemoteRunDataSource
import com.zeljkolucic.run.network.KtorRemoteRunDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    singleOf(::KtorRemoteRunDataSource).bind<RemoteRunDataSource>()
}