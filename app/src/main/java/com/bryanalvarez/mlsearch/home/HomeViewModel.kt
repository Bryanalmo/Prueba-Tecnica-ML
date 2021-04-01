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
    var categoryError = MutableLiveData<Failure>()

    fun getCategories(): LiveData<List<Category>> {
        categoriesList = MutableLiveData()
        getCategoriesData()
        return categoriesList
    }

    private fun getCategoriesData() {
        loadingCategories = true
        notifyChange()
        getCategories.execute(null){either ->
            either.fold(
                {
                    Log.d("MYLOG ERROR", "error -> ${it.exception.localizedMessage}")
                    categoryError.postValue(it)
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