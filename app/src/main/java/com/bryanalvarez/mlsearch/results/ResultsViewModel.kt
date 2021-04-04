package com.bryanalvarez.mlsearch.results

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bryanalvarez.domain.constants.PAGE_LIMIT
import com.bryanalvarez.domain.interactors.AddLastSeenItem
import com.bryanalvarez.domain.interactors.GetItemsByCategory
import com.bryanalvarez.domain.interactors.GetItemsBySearch
import com.bryanalvarez.domain.models.Category
import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.domain.models.ItemsListInfo
import com.bryanalvarez.mlsearch.core.ObservableViewModel

class ResultsViewModel(private val getItemsBySearch: GetItemsBySearch,
                       private val getItemsByCategory: GetItemsByCategory,
                       private val addLastSeenItem: AddLastSeenItem): ObservableViewModel() {

    private lateinit var itemsList: MutableLiveData<MutableList<Item>>
    var itemsListInfo: ItemsListInfo? = null
    var searchText: String = ""
    var categorySelected = Category()
    var itemsResultsError = MutableLiveData<String>()
    var loadingItemsList = false
    var loadingPagingItemsList = false
    var itemsListIsEmpty = false
    var currentOffset = 0
    var bySearch = false
    var isLastPage = false

    /**
     * function to instantiate the itemsList (if it hasn't been) and return returns it to be observe
     */
    fun getItemsList(): LiveData<MutableList<Item>>{
        if(!::itemsList.isInitialized){
            itemsList = MutableLiveData()
            callItemsListFromSource()
        }
        return itemsList
    }

    /**
     * function to verify if it should bring items from categoryes or by search
     */
    fun callItemsListFromSource() {
        if(bySearch) getItemsBySearchData() else getItemsByCategoryData()
    }

    /**
     * function to execute the GetItemsBySearch interactor to bring the items by search list
     */
    private fun getItemsBySearchData() {
        enableDisableLoading(true)
        val params = GetItemsBySearch.Params(searchText, currentOffset)
        getItemsBySearch.execute(params){either ->
            either.fold(
                {
                    Log.d("MYLOG ERROR", "error -> ${it.exception.localizedMessage}")
                    itemsResultsError.postValue(it.exception.localizedMessage)
                    enableDisableLoading(false)
                },{
                    Log.d("MYLOG", "items -> $it")
                    handleItemListResponse(it)
                }
            )
        }
    }

    /**
     * function to activate or deactivate the loading variables
     */
    private fun enableDisableLoading(loading: Boolean){
        if(itemsListInfo == null){
            loadingItemsList = loading
        }else{
            loadingPagingItemsList = loading
        }
        notifyChange()
    }

    /**
     * function to handle the response, it updates the list and the currentOffset for pagination purposes
     */
    private fun handleItemListResponse(responseInfo: ItemsListInfo){
        currentOffset = responseInfo.paging.offset + PAGE_LIMIT
        enableDisableLoading(false)
        if(itemsListInfo == null){
            itemsListInfo = responseInfo
            itemsList.postValue(responseInfo.results)
            itemsListIsEmpty = responseInfo.results.isEmpty()
        }else{
            var updatedItems = itemsList.value
            updatedItems?.addAll(responseInfo.results)
            itemsList.postValue(updatedItems ?: mutableListOf())
            isLastPage = responseInfo.results.isEmpty()
        }
    }

    /**
     * function to execute the GetItemsByCategory interactor to bring the item by category list
     */
    private fun getItemsByCategoryData() {
        enableDisableLoading(true)
        val params = GetItemsByCategory.Params(categorySelected.id!!, currentOffset)
        getItemsByCategory.execute(params){either ->
            either.fold(
                {
                    Log.d("MYLOG ERROR", "error -> ${it.exception.localizedMessage}")
                    itemsResultsError.postValue(it.exception.localizedMessage)
                    enableDisableLoading(false)
                },{
                    Log.d("MYLOG", "items by category -> $it")
                    handleItemListResponse(it)
                }
            )
        }
    }

    /**
     * function to execute the AddLastSeenItem interactor to add the item
     * @param item item to be added
     */
    fun addItemToLastSeen(item: Item) {
        val params = AddLastSeenItem.Params(item)
        addLastSeenItem.execute(params){either ->
            either.fold(
                {
                    Log.d("MYLOG ERROR", "error -> ${it.exception.localizedMessage}")
                    itemsResultsError.postValue(it.exception.localizedMessage)
                },{
                }
            )
        }
    }
}