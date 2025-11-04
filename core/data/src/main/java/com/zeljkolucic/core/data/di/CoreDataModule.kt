package com.zeljkolucic.core.data.di

import com.zeljkolucic.core.data.auth.EncryptedSessionStorage
import com.zeljkolucic.core.data.networking.HttpClientFactory
import com.zeljkolucic.core.domain.SessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory(get()).build()
    }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
}