package com.bryanalvarez.mlsearch.di

import com.bryanalvarez.domain.interactors.*
import com.bryanalvarez.domain.repository.Repository
import org.koin.dsl.module

val interactorModule = module {

    fun provideGetItemsBySearchInteractor(repository: Repository): GetItemsBySearch = GetItemsBySearch(repository)
    fun provideGetUserRecentSearchInteractor(repository: Repository): GetUserRecentSearch = GetUserRecentSearch(repository)
    fun provideAddUserSearchInteractor(repository: Repository): AddUserSearch = AddUserSearch(repository)
    fun provideGetCategoriesInteractor(repository: Repository): GetCategories = GetCategories(repository)
    fun provideGetItemsByCategoryInteractor(repository: Repository): GetItemsByCategory = GetItemsByCategory(repository)
    fun provideGetItemsBySellerInteractor(repository: Repository): GetItemsBySeller = GetItemsBySeller(repository)

    single { provideGetItemsBySearchInteractor(get()) }
    single { provideGetUserRecentSearchInteractor(get()) }
    single { provideAddUserSearchInteractor(get()) }
    single { provideGetCategoriesInteractor(get()) }
    single { provideGetItemsByCategoryInteractor(get()) }
    single { provideGetItemsBySellerInteractor(get()) }
}