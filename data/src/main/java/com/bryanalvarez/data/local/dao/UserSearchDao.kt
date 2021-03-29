package com.bryanalvarez.data.local.dao

import androidx.room.*
import com.bryanalvarez.domain.models.UserSearch

@Dao
interface UserSearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userSearch: UserSearch)

    @Transaction
    @Query("SELECT * FROM userSearch")
    fun getUserSearchList(): List<UserSearch>
}