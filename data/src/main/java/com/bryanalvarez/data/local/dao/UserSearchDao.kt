package com.bryanalvarez.data.local.dao

import androidx.room.*
import com.bryanalvarez.domain.models.UserSearch

@Dao
interface UserSearchDao {

    /**
     * function to insert a userSearch record in the Room local Database
     * @param userSearch new userSearch to be saved
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userSearch: UserSearch)

    /**
     * function to get all the userSearches saved in the local database
     */
    @Transaction
    @Query("SELECT * FROM userSearch")
    fun getUserSearchList(): List<UserSearch>
}