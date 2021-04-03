package com.bryanalvarez.mlsearch.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import com.bryanalvarez.domain.interactors.GetCategories
import com.bryanalvarez.mlsearch.mockRepository.AppMockedRepository
import com.bryanalvarez.mlsearch.utils.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class HomeViewModelTest{

    private lateinit var homeViewModel: HomeViewModel
    private var repository =
        AppMockedRepository()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup(){
        Dispatchers.setMain(mainThreadSurrogate)
        var getCategoriesInteractor = GetCategories(repository)
        homeViewModel =
            HomeViewModel(getCategoriesInteractor)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `get categories data, return list`() = runBlocking{
        val categoriesList = homeViewModel.getCategories().getOrAwaitValue()
        assertThat(categoriesList).isNotEmpty()
    }

    @Test
    fun `get categories data, returns error`() = runBlocking{
        repository.setShouldReturnError(true)
        homeViewModel.getCategories()
        val errorValue = homeViewModel.categoryError.getOrAwaitValue()
        print(errorValue)
        assertThat(errorValue).isNotNull()
    }

}