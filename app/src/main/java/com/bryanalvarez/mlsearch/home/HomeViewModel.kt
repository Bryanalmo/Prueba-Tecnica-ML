package com.bryanalvarez.mlsearch.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bryanalvarez.domain.interactors.GetCategories
import com.bryanalvarez.domain.interactors.GetLastSeenItem
import com.bryanalvarez.domain.models.Category
import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.mlsearch.core.ObservableViewModel

class HomeViewModel(private val getCategories: GetCategories,
                    private val getLastSeenItem: GetLastSeenItem): ObservableViewModel() {

    private lateinit var categoriesList: MutableLiveData<List<Category>>
    var urlLastSeenItem: MutableLiveData<String> = MutableLiveData()
    var loadingCategories = false
    var loadingLastSeenItem = false
    var lastSeenItemEmpty = false
    var homeError = MutableLiveData<String>()
    var lastSeenItem = Item()

    /**
     * function to instantiate the categoriesList and returns it to be observe
     */
    fun getCategories(): LiveData<List<Category>> {
        if(!::categoriesList.isInitialized){
            categoriesList = MutableLiveData()
            getCategoriesData()
        }
        return categoriesList
    }

    /**
     * function to execute the GetCategories interactor to bring the categories list
     */
    private fun getCategoriesData() {
        loadingCategories = true
        notifyChange()
        getCategories.execute(null){either ->
            either.fold(
                {
                    Log.d("MYLOG ERROR", "error -> ${it.exception.localizedMessage}")
                    homeError.postValue(it.exception.localizedMessage)
                    loadingCategories = false
                    notifyChange()
                },{
                    Log.d("MYLOG", "categories -> $it")
                    categoriesList.postValue(it)
                    loadingCategories = false
                    notifyChange()
                }
            )
        }
    }

    /**
     * function to execute the GetLastSeenItem interactor to bring the last seen item
     */
    fun getLastSeenItem() {
        Log.d("MYLOG", "call getLastSeenItem")
        loadingLastSeenItem = true
        notifyChange()
        getLastSeenItem.execute(null){either ->
            either.fold(
                {
                    Log.d("MYLOG ERROR", "error -> ${it.exception.localizedMessage}")
                    homeError.postValue(it.exception.localizedMessage)
                    lastSeenItemEmpty = true
                    loadingLastSeenItem = false
                    notifyChange()
                },{item ->
                    Log.d("MYLOG", "getLastSeenItem -> $item")
                    if(item != null){
                        lastSeenItem = item
                        urlLastSeenItem.postValue(item.thumbnail)
                        lastSeenItemEmpty = false
                    }else{
                        lastSeenItemEmpty = true
                    }
                    loadingLastSeenItem = false
                    notifyChange()
                }
            )
        }
    }
}