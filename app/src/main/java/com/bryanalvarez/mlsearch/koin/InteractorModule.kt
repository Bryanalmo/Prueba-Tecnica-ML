package com.bryanalvarez.mlsearch.koin

import com.bryanalvarez.domain.interactors.AddUserSearch
import com.bryanalvarez.domain.interactors.GetItemsBySearch
import com.bryanalvarez.domain.interactors.GetUserRecentSearch
import com.bryanalvarez.domain.repository.Repository
import org.koin.dsl.module

val interactorModule = module {

    fun provideGetItemsBySearchInteractor(repository: Repository): GetItemsBySearch = GetItemsBySearch(repository)
    fun provideGetUserRecentSearchInteractor(repository: Repository): GetUserRecentSearch = GetUserRecentSearch(repository)
    fun provideAddUserSearchInteractor(repository: Repository): AddUserSearch = AddUserSearch(repository)

    single { provideGetItemsBySearchInteractor(get()) }
    single { provideGetUserRecentSearchInteractor(get()) }
    single { provideAddUserSearchInteractor(get()) }
}