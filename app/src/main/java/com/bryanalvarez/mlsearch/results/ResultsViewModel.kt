package com.bryanalvarez.mlsearch.results

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bryanalvarez.domain.interactors.GetItemsByCategory
import com.bryanalvarez.domain.interactors.GetItemsBySearch
import com.bryanalvarez.domain.models.Category
import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.mlsearch.core.ObservableViewModel

class ResultsViewModel(private val getItemsBySearch: GetItemsBySearch,
                       private val getItemsByCategory: GetItemsByCategory): ObservableViewModel() {

    private lateinit var itemsList: MutableLiveData<List<Item>>
    var searchText: String = ""
    var categorySelected = Category()

    fun getItemsList(bySearch: Boolean): LiveData<List<Item>>{
        itemsList = MutableLiveData()
        if(bySearch) getItemsBySearchData() else getItemsByCategoryData()
        return itemsList
    }

    private fun getItemsBySearchData() {
        val params = GetItemsBySearch.Params(searchText)
        getItemsBySearch.execute(params){either ->
            either.fold(
                {
                    Log.d("MYLOG ERROR", "error -> ${it.exception.localizedMessage}")
                },{
                    Log.d("MYLOG", "items -> $it")
                    itemsList.postValue(it)
                }
            )
        }
    }

    private fun getItemsByCategoryData() {
        val params = GetItemsByCategory.Params(categorySelected.id!!)
        getItemsByCategory.execute(params){either ->
            either.fold(
                {
                    Log.d("MYLOG ERROR", "error -> ${it.exception.localizedMessage}")
                },{
                    Log.d("MYLOG", "items by category -> $it")
                    itemsList.postValue(it)
                }
            )
        }
    }
}