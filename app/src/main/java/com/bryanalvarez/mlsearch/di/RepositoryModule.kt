package com.bryanalvarez.mlsearch.di

import android.content.Context
import com.bryanalvarez.data.local.MLDataBase
import com.bryanalvarez.data.local.repository.LastSeenItemRepository
import com.bryanalvarez.data.local.repository.UserSearchRepository
import com.bryanalvarez.data.remote.AppRepository
import com.bryanalvarez.data.remote.Service
import com.bryanalvarez.domain.repository.Repository
import org.koin.dsl.module

/**
 * module that contains and provides the local and external repositories,
 * also provides the Service (retrofit instance)
 */
val repositoryModule = module {

    fun provideRepository(
        service: Service,
        userSearchRepository: UserSearchRepository): Repository = AppRepository(service, userSearchRepository)
    fun provideService(context: Context): Service = Service.getService(context)
    fun provideUserSearchRepository(context: Context): UserSearchRepository = UserSearchRepository(
        MLDataBase.getDatabase(context).userSearchDao())
    fun provideLastSeenItemRepository(context: Context): LastSeenItemRepository = LastSeenItemRepository(
        MLDataBase.getDatabase(context).lastSeenItemDao())

    single { provideRepository(get(), get()) }
    single { provideService(get()) }
    single { provideUserSearchRepository( get()) }
}