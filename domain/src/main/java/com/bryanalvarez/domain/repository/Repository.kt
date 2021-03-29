package com.bryanalvarez.domain.repository

import arrow.core.Either
import arrow.core.Failure
import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.domain.models.UserSearch

interface Repository {
    suspend fun getItemsBySearch(text: String): Either<Failure, List<Item>>

    suspend fun getUserRecentSearch(): Either<Failure, List<UserSearch>>

    suspend fun addUserSearch(text: String): Either<Failure, Boolean>
}