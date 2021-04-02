package com.bryanalvarez.mlsearch.results

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bryanalvarez.domain.constants.PAGE_LIMIT
import com.bryanalvarez.domain.interactors.GetItemsByCategory
import com.bryanalvarez.domain.interactors.GetItemsBySearch
import com.bryanalvarez.domain.models.Category
import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.domain.models.ItemsListInfo
import com.bryanalvarez.mlsearch.core.ObservableViewModel

class ResultsViewModel(private val getItemsBySearch: GetItemsBySearch,
                       private val getItemsByCategory: GetItemsByCategory): ObservableViewModel() {

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

    fun getItemsList(): LiveData<MutableList<Item>>{
        if(!::itemsList.isInitialized){
            itemsList = MutableLiveData()
        }
        if(bySearch) getItemsBySearchData() else getItemsByCategoryData()
        return itemsList
    }

    private fun getItemsBySearchData() {
        enableDesableLoading(true)
        val params = GetItemsBySearch.Params(searchText, currentOffset)
        getItemsBySearch.execute(params){either ->
            either.fold(
                {
                    Log.d("MYLOG ERROR", "error -> ${it.exception.localizedMessage}")
                    itemsResultsError.postValue(it.exception.localizedMessage)
                    enableDesableLoading(false)
                },{
                    Log.d("MYLOG", "items -> $it")
                    handleItemListResponse(it)
                }
            )
        }
    }

    private fun enableDesableLoading(loading: Boolean){
        if(itemsListInfo == null){
            loadingItemsList = loading
        }else{
            loadingPagingItemsList = loading
        }
        notifyChange()
    }

    private fun handleItemListResponse(responseInfo: ItemsListInfo){
        currentOffset = responseInfo.paging.offset + PAGE_LIMIT
        enableDesableLoading(false)
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

    private fun getItemsByCategoryData() {
        enableDesableLoading(true)
        val params = GetItemsByCategory.Params(categorySelected.id!!, currentOffset)
        getItemsByCategory.execute(params){either ->
            either.fold(
                {
                    Log.d("MYLOG ERROR", "error -> ${it.exception.localizedMessage}")
                    itemsResultsError.postValue(it.exception.localizedMessage)
                    enableDesableLoading(false)
                },{
                    Log.d("MYLOG", "items by category -> $it")
                    handleItemListResponse(it)
                }
            )
        }
    }
}