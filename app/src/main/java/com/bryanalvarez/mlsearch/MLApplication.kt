package com.bryanalvarez.mlsearch

import android.app.Application
import com.bryanalvarez.mlsearch.di.interactorModule
import com.bryanalvarez.mlsearch.di.repositoryModule
import com.bryanalvarez.mlsearch.di.viewModelModule
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