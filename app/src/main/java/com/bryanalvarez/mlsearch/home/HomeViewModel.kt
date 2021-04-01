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
    var categoryError = MutableLiveData<Failure>()

    fun getCategories(): LiveData<List<Category>> {
        categoriesList = MutableLiveData()
        getCategoriesData()
        return categoriesList
    }

    private fun getCategoriesData() {
        getCategories.execute(null){either ->
            either.fold(
                {
                    Log.d("MYLOG ERROR", "error -> ${it.exception.localizedMessage}")
                    categoryError.postValue(it)
                },{
                    Log.d("MYLOG", "categories -> $it")
                    try{
                        categoriesList.postValue(it.subList(0,8))
                    }catch (e: Exception){
                        categoriesList.postValue(it)
                    }
                }
            )
        }
    }
}