package com.bryanalvarez.mlsearch.di

import android.content.Context
import com.bryanalvarez.data.local.MLDataBase
import com.bryanalvarez.data.local.repository.UserSearchRepository
import com.bryanalvarez.data.remote.AppRepository
import com.bryanalvarez.data.remote.Service
import com.bryanalvarez.domain.repository.Repository
import org.koin.dsl.module

val repositoryModule = module {

    fun provideRepository(
        service: Service,
        userSearchRepository: UserSearchRepository): Repository = AppRepository(service, userSearchRepository)
    fun provideService(): Service = Service.getService()
    fun provideUserSearchRepository(context: Context): UserSearchRepository = UserSearchRepository(
        MLDataBase.getDatabase(context).userSearchDao())

    single { provideRepository(get(), get()) }
    single { provideService() }
    single { provideUserSearchRepository( get()) }
}