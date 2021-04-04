package com.bryanalvarez.data.local.dao

import androidx.room.*
import com.bryanalvarez.domain.models.Item

@Dao
interface LastSeenItemDao {

    /**
     * function to insert a last seen item record in the Room local Database
     * @param item new item to be saved
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Item)

    /**
     * function to get the las seen item saved in the local database
     */
    @Transaction
    @Query("SELECT * FROM lastSeenItem LIMIT 1")
    fun getLastSeenItem(): Item
}