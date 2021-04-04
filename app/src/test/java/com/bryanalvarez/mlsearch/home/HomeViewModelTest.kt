package com.bryanalvarez.mlsearch.home

import android.os.Handler
import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import com.bryanalvarez.domain.interactors.GetCategories
import com.bryanalvarez.domain.interactors.GetLastSeenItem
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
        var getLastSeenItem = GetLastSeenItem(repository)
        homeViewModel =
            HomeViewModel(getCategoriesInteractor, getLastSeenItem)
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
        val errorValue = homeViewModel.homeError.getOrAwaitValue()
        print(errorValue)
        assertThat(errorValue).isNotNull()
    }

    @Test
    fun `get last seen item, returns item data`() = runBlocking{
        homeViewModel.getLastSeenItem()
        val urlItem = homeViewModel.urlLastSeenItem.getOrAwaitValue()
        var lastItem = homeViewModel.lastSeenItem
        print(lastItem)
        assertThat(lastItem).isNotNull()
        assertThat(urlItem).isNotEmpty()
    }

    @Test
    fun `get last seen item, returns null`() = runBlocking{
        repository.setShouldReturnError(true)
        homeViewModel.getLastSeenItem()
        Thread.sleep(1000)
        var errorValue = homeViewModel.lastSeenItemEmpty
        print(homeViewModel.lastSeenItem)
        print(errorValue)
        assertThat(errorValue).isTrue()
    }

}