package com.bryanalvarez.mlsearch.itemDetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bryanalvarez.domain.interactors.GetItemsBySeller
import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.domain.models.Seller
import com.bryanalvarez.mlsearch.mockRepository.AppMockedRepository
import com.bryanalvarez.mlsearch.utils.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
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
class ItemDetailViewModelTest {

    private lateinit var itemDetailViewModel: ItemDetailViewModel
    private var repository =
        AppMockedRepository()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup(){
        Dispatchers.setMain(mainThreadSurrogate)
        var getItemsBySellerInteractor = GetItemsBySeller(repository)
        itemDetailViewModel = ItemDetailViewModel(getItemsBySellerInteractor)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `get items by seller, return list`() = runBlocking{
        val item = Item(seller = Seller(id = "id"))
        val itemsList = itemDetailViewModel.getItemsBySeller(item).getOrAwaitValue()
        print(itemsList)
        assertThat(itemDetailViewModel.seller.id).isEqualTo("id")
        assertThat(itemsList).isNotEmpty()
    }

    @Test
    fun `get items by seller, returns error`() = runBlocking{
        repository.setShouldReturnError(true)
        val item = Item(seller = Seller(id = ""))
        itemDetailViewModel.getItemsBySeller(item)
        val errorValue = itemDetailViewModel.itemBySellerError.getOrAwaitValue()
        print(errorValue)
        assertThat(errorValue).isNotNull()
    }
}