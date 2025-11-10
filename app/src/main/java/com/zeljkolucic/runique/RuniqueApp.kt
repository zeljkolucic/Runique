package com.zeljkolucic.runique

import android.app.Application
import com.zeljkolucic.auth.data.di.authDataModule
import com.zeljkolucic.auth.presentation.di.authViewModelModule
import com.zeljkolucic.core.data.di.coreDataModule
import com.zeljkolucic.run.presentation.di.runViewModelModule
import com.zeljkolucic.runique.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

class RuniqueApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@RuniqueApp)
            modules(
                authDataModule,
                authViewModelModule,
                appModule,
                coreDataModule,
                runViewModelModule
            )
        }
    }
}