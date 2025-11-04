package com.zeljkolucic.auth.data.di

import com.zeljkolucic.auth.data.EmailPatternValidator
import com.zeljkolucic.auth.data.RemoteAuthRepository
import com.zeljkolucic.auth.domain.AuthRepository
import com.zeljkolucic.auth.domain.PatternValidator
import com.zeljkolucic.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
    singleOf(::RemoteAuthRepository).bind<AuthRepository>()
}

