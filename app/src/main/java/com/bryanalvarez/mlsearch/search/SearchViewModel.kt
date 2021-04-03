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
    var recentSearchesError = MutableLiveData<String>()
    var loadingUserSearchList = false
    var userSearchListIsEmpty = false

    /**
     * function to instantiate the userSearchList and returns it to be observe
     */
    fun getUserSearchList(): LiveData<List<UserSearch>> {
        if(!::userSearchList.isInitialized){
            userSearchList = MutableLiveData()
            getUserSearchListData()
        }
        return userSearchList
    }

    /**
     * function to execute the GetUserRecentSearch interactor to bring the searches list
     */
    private fun getUserSearchListData() {
        loadingUserSearchList = true
        notifyChange()
        getUserRecentSearch.execute(null){either ->
            either.fold(
                {
                    Log.d("MYLOG ERROR", "error -> ${it.exception.localizedMessage}")
                    recentSearchesError.postValue(it.exception.localizedMessage)
                    loadingUserSearchList = false
                    notifyChange()
                },{
                    Log.d("MYLOG", "getUserSearchListData (is empty ${it.isEmpty()}) -> $it")
                    userSearchList.postValue(it)
                    loadingUserSearchList = false
                    userSearchListIsEmpty = it.isEmpty()
                    notifyChange()
                }
            )
        }
    }

    /**
     * function to execute the AddUserSearch interactor and save a new user search locally
     * @param search new search typed by the user
     */
    fun addUserSearch(search: String) {
        addUserSearch.execute(AddUserSearch.Params(search)){either ->
            either.fold(
                {
                    Log.d("MYLOG ERROR", "error -> ${it.exception.localizedMessage}")
                    recentSearchesError.postValue(it.exception.localizedMessage)
                },{
                    Log.d("MYLOG", "addUserSearch -> $it")
                }
            )
        }
    }
}