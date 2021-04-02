package com.bryanalvarez.mlsearch.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.core.Failure
import com.bryanalvarez.domain.interactors.GetCategories
import com.bryanalvarez.domain.models.Category
import com.bryanalvarez.mlsearch.core.ObservableViewModel

class HomeViewModel(private val getCategories: GetCategories): ObservableViewModel() {

    private lateinit var categoriesList: MutableLiveData<List<Category>>
    var loadingCategories = false
    var categoryError = MutableLiveData<String>()

    /**
     * function to instantiate the categoriesList and returns it to be observe
     */
    fun getCategories(): LiveData<List<Category>> {
        categoriesList = MutableLiveData()
        getCategoriesData()
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
                    categoryError.postValue(it.exception.localizedMessage)
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
}