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
    fun provideService(context: Context): Service = Service.getService(context)
    fun provideUserSearchRepository(context: Context): UserSearchRepository = UserSearchRepository(
        MLDataBase.getDatabase(context).userSearchDao())

    single { provideRepository(get(), get()) }
    single { provideService(get()) }
    single { provideUserSearchRepository( get()) }
}