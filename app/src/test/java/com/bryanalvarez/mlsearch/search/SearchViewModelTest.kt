package com.bryanalvarez.mlsearch.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bryanalvarez.domain.interactors.AddUserSearch
import com.bryanalvarez.domain.interactors.GetUserRecentSearch
import com.bryanalvarez.domain.models.UserSearch
import com.bryanalvarez.mlsearch.mockRepository.AppMockedRepository
import com.bryanalvarez.mlsearch.utils.getOrAwaitValue
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
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
class SearchViewModelTest{

    private lateinit var searchViewModel: SearchViewModel
    private var repository =
        AppMockedRepository()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup(){
        Dispatchers.setMain(mainThreadSurrogate)
        var getUserRecentSearch = GetUserRecentSearch(repository)
        var addUserSearch = AddUserSearch(repository)
        searchViewModel = SearchViewModel(getUserRecentSearch, addUserSearch)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `get recent searches, return list`() = runBlocking{
        val recentSearchesList = searchViewModel.getUserSearchList().getOrAwaitValue()
        print(recentSearchesList)
        Truth.assertThat(recentSearchesList).isNotEmpty()
    }

    @Test
    fun `get recent searches, returns error`() = runBlocking{
        repository.setShouldReturnError(true)
        searchViewModel.getUserSearchList()
        val errorValue = searchViewModel.recentSearchesError.getOrAwaitValue()
        print(errorValue)
        Truth.assertThat(errorValue).isNotNull()
    }

    @Test
    fun `add new search, returns item in list`() = runBlocking{
        val newSearch = UserSearch("Nueva busqueda")
        searchViewModel.addUserSearch(newSearch.textSearched)
        val recentSearchesList = searchViewModel.getUserSearchList().getOrAwaitValue()
        print(recentSearchesList)
        Truth.assertThat(recentSearchesList).contains(newSearch)
    }
}