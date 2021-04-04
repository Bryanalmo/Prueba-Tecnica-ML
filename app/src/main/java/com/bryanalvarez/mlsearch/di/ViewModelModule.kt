package com.bryanalvarez.mlsearch.di

import com.bryanalvarez.mlsearch.home.HomeViewModel
import com.bryanalvarez.mlsearch.itemDetail.ItemDetailViewModel
import com.bryanalvarez.mlsearch.results.ResultsViewModel
import com.bryanalvarez.mlsearch.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
/**
 * module that contains and provides all the viewModels
 */
val viewModelModule = module {
    viewModel { ResultsViewModel(get(), get(), get()) }
    viewModel { SearchViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { ItemDetailViewModel(get()) }
}