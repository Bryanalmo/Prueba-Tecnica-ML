package com.bryanalvarez.mlsearch.di

import com.bryanalvarez.domain.interactors.*
import com.bryanalvarez.domain.repository.Repository
import org.koin.dsl.module

/**
 * module that contains and provides all the interactors
 */
val interactorModule = module {

    fun provideGetItemsBySearchInteractor(repository: Repository): GetItemsBySearch = GetItemsBySearch(repository)
    fun provideGetUserRecentSearchInteractor(repository: Repository): GetUserRecentSearch = GetUserRecentSearch(repository)
    fun provideAddUserSearchInteractor(repository: Repository): AddUserSearch = AddUserSearch(repository)
    fun provideGetCategoriesInteractor(repository: Repository): GetCategories = GetCategories(repository)
    fun provideGetItemsByCategoryInteractor(repository: Repository): GetItemsByCategory = GetItemsByCategory(repository)
    fun provideGetItemsBySellerInteractor(repository: Repository): GetItemsBySeller = GetItemsBySeller(repository)
    fun provideGetLastSeenItemInteractor(repository: Repository): GetLastSeenItem = GetLastSeenItem(repository)
    fun provideAddLastSeenItemInteractor(repository: Repository): AddLastSeenItem = AddLastSeenItem(repository)

    single { provideGetItemsBySearchInteractor(get()) }
    single { provideGetUserRecentSearchInteractor(get()) }
    single { provideAddUserSearchInteractor(get()) }
    single { provideGetCategoriesInteractor(get()) }
    single { provideGetItemsByCategoryInteractor(get()) }
    single { provideGetItemsBySellerInteractor(get()) }
    single { provideGetLastSeenItemInteractor(get()) }
    single { provideAddLastSeenItemInteractor(get()) }
}