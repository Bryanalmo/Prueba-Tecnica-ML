package com.bryanalvarez.mlsearch.koin

import com.bryanalvarez.mlsearch.home.HomeViewModel
import com.bryanalvarez.mlsearch.results.ResultsViewModel
import com.bryanalvarez.mlsearch.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ResultsViewModel(get(), get()) }
    viewModel { SearchViewModel(get(), get()) }
    viewModel { HomeViewModel(get()) }
}