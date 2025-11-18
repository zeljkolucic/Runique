package com.zeljkolucic.run.location.di

import com.zeljkolucic.run.domain.LocationObserver
import com.zeljkolucic.run.location.AndroidLocationObserver
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val locationModule = module {
    singleOf(::AndroidLocationObserver).bind<LocationObserver>()
}