package com.bryanalvarez.mlsearch.results

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bryanalvarez.domain.interactors.GetItemsBySearch
import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.mlsearch.core.ObservableViewModel

class ResultsViewModel(private val getItemsBySearch: GetItemsBySearch): ObservableViewModel() {

    private lateinit var itemsList: MutableLiveData<List<Item>>
    var searchText: String = ""

    fun getItemsBySearch(search: String): LiveData<List<Item>>{
        Log.d("MYLOG", "getItemsBySearch -> $search")
        searchText = search
        notifyChange()
        itemsList = MutableLiveData()
        getItemsBySearchData(search)
        return itemsList
    }

    private fun getItemsBySearchData(search: String) {
        val params = GetItemsBySearch.Params(search)
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
}