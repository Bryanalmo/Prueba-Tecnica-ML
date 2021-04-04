package com.bryanalvarez.data.local.repository

import com.bryanalvarez.data.local.dao.LastSeenItemDao
import com.bryanalvarez.domain.models.Item

class LastSeenItemRepository (private val lastSeenItemDao: LastSeenItemDao) {

    /**
     * function to get the last seen item
     */
    fun getLastSeenItem(): Item {
        return lastSeenItemDao.getLastSeenItem()
    }

    /**
     * function to insert a last seen item record in the Room local Database
     * @param item new item to be saved
     */
    fun insertItem(item: Item){
        lastSeenItemDao.insert(item)
    }

    /**
     * function to delete all items saved in the Room local Database
     */
    fun deleteAll(){
        lastSeenItemDao.deleteAll()
    }
}