package com.bryanalvarez.data.local.repository

import com.bryanalvarez.data.local.dao.UserSearchDao
import com.bryanalvarez.domain.models.UserSearch

class UserSearchRepository(private val userSearchDao: UserSearchDao) {

    fun getUserSearchList(): List<UserSearch>{
        return userSearchDao.getUserSearchList()
    }

    fun insertItem(cartItem: UserSearch){
        userSearchDao.insert(cartItem)
    }
}