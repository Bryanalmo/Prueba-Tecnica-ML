package com.bryanalvarez.data.local.repository

import com.bryanalvarez.data.local.dao.UserSearchDao
import com.bryanalvarez.domain.models.UserSearch

class UserSearchRepository(private val userSearchDao: UserSearchDao) {

    /**
     * function to get all the user searches
     */
    fun getUserSearchList(): List<UserSearch>{
        return userSearchDao.getUserSearchList()
    }

    /**
     * function to insert a userSearch record in the Room local Database
     * @param userSearch new userSearch to be saved
     */
    fun insertItem(userSearch: UserSearch){
        userSearchDao.insert(userSearch)
    }
}