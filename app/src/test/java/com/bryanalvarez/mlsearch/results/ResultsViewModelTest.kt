package com.bryanalvarez.mlsearch.results

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bryanalvarez.domain.interactors.AddLastSeenItem
import com.bryanalvarez.domain.interactors.GetItemsByCategory
import com.bryanalvarez.domain.interactors.GetItemsBySearch
import com.bryanalvarez.domain.models.Category
import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.domain.models.Seller
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
class ResultsViewModelTest {

    private lateinit var resultsViewModel: ResultsViewModel
    private var repository =
        AppMockedRepository()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup(){
        Dispatchers.setMain(mainThreadSurrogate)
        val getItemsBySearch = GetItemsBySearch(repository)
        val getItemsByCategory = GetItemsByCategory(repository)
        val addLastSeenItem = AddLastSeenItem(repository)
        resultsViewModel = ResultsViewModel(getItemsBySearch, getItemsByCategory, addLastSeenItem)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `get items by search, return list`() = runBlocking{
        resultsViewModel.searchText = "Iphone"
        resultsViewModel.bySearch = true
        val itemsListBySearch = resultsViewModel.getItemsList().getOrAwaitValue()
        print(itemsListBySearch)
        Truth.assertThat(itemsListBySearch).isNotEmpty()
    }

    @Test
    fun `get items by seller, returns error`() = runBlocking{
        repository.setShouldReturnError(true)
        resultsViewModel.searchText = ""
        resultsViewModel.bySearch = true
        resultsViewModel.getItemsList()
        val errorValue = resultsViewModel.itemsResultsError.getOrAwaitValue()
        print(errorValue)
        Truth.assertThat(errorValue).isNotNull()
    }

    @Test
    fun `get items by category, return list`() = runBlocking{
        resultsViewModel.categorySelected = Category("id", "cat 1")
        resultsViewModel.bySearch = false
        val itemsListByCategory = resultsViewModel.getItemsList().getOrAwaitValue()
        print(itemsListByCategory)
        Truth.assertThat(itemsListByCategory).isNotEmpty()
    }

    @Test
    fun `get items by category, returns error`() = runBlocking{
        repository.setShouldReturnError(true)
        resultsViewModel.categorySelected = Category(id = "")
        resultsViewModel.bySearch = false
        resultsViewModel.getItemsList()
        val errorValue = resultsViewModel.itemsResultsError.getOrAwaitValue()
        print(errorValue)
        Truth.assertThat(errorValue).isNotNull()
    }

    @Test
    fun `add new last seen item, returns item in list`() = runBlocking{
        val lastSeenItem = Item(title = "Armario", seller = Seller(idSeller = "id3"))
        resultsViewModel.addItemToLastSeen(lastSeenItem)
        Thread.sleep(1000)
        val lastSeenItemList = repository.lastSeenItemsList
        print(lastSeenItemList)
        Truth.assertThat(lastSeenItemList).contains(lastSeenItem)
    }
}