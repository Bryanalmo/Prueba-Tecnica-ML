package com.bryanalvarez.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userSearch")
data class UserSearch(
    @PrimaryKey val textSearched: String
)