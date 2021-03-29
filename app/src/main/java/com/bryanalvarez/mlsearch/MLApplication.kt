package com.bryanalvarez.mlsearch

import android.app.Application
import com.bryanalvarez.mlsearch.koin.interactorModule
import com.bryanalvarez.mlsearch.koin.repositoryModule
import com.bryanalvarez.mlsearch.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MLApplication: Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MLApplication)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    interactorModule
                )
            )
        }
    }
}