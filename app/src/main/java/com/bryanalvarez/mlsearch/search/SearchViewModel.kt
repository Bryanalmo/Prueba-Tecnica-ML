package com.bryanalvarez.mlsearch.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.core.Failure
import com.bryanalvarez.domain.interactors.AddUserSearch
import com.bryanalvarez.domain.interactors.GetUserRecentSearch
import com.bryanalvarez.domain.models.UserSearch
import com.bryanalvarez.mlsearch.core.ObservableViewModel

class SearchViewModel(private val getUserRecentSearch: GetUserRecentSearch ,
                      private val addUserSearch: AddUserSearch): ObservableViewModel() {

    private lateinit var userSearchList: MutableLiveData<List<UserSearch>>
    var recentSearchesError = MutableLiveData<Failure>()

    fun getUserSearchList(): LiveData<List<UserSearch>> {
        userSearchList = MutableLiveData()
        getUserSearchListData()
        return userSearchList
    }

    private fun getUserSearchListData() {
        getUserRecentSearch.execute(null){either ->
            either.fold(
                {
                    Log.d("MYLOG ERROR", "error -> ${it.exception.localizedMessage}")
                    recentSearchesError.postValue(it)
                },{
                    Log.d("MYLOG", "getUserSearchListData -> $it")
                    userSearchList.postValue(it)
                }
            )
        }
    }

    fun addUserSearch(search: String) {
        addUserSearch.execute(AddUserSearch.Params(search)){either ->
            either.fold(
                {
                    Log.d("MYLOG ERROR", "error -> ${it.exception.localizedMessage}")
                    recentSearchesError.postValue(it)
                },{
                    Log.d("MYLOG", "addUserSearch -> $it")
                }
            )
        }
    }
}