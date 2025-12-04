package com.zeljkolucic.core.data.di

import com.zeljkolucic.core.data.OfflineFirstRunRepository
import com.zeljkolucic.core.data.auth.EncryptedSessionStorage
import com.zeljkolucic.core.data.networking.HttpClientFactory
import com.zeljkolucic.core.domain.SessionStorage
import com.zeljkolucic.core.domain.run.RunRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory(get()).build()
    }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()

    singleOf(::OfflineFirstRunRepository).bind<RunRepository>()
}