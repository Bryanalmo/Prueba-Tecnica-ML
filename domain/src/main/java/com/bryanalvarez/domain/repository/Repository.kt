package com.bryanalvarez.domain.repository

import arrow.core.Either
import arrow.core.Failure
import com.bryanalvarez.domain.models.*

interface Repository {
    suspend fun getItemsBySearch(text: String, offset: Int): Either<Failure, ItemsListInfo>

    suspend fun getUserRecentSearch(): Either<Failure, List<UserSearch>>

    suspend fun addUserSearch(text: String): Either<Failure, Boolean>

    suspend fun getCategories(): Either<Failure, List<Category>>

    suspend fun getItemsByCategory(categoryId: String, offset: Int): Either<Failure, ItemsListInfo>

    suspend fun getItemsBySeller(sellerId: String): Either<Failure, SellerInfo>

    suspend fun getLastSeenItem(): Either<Failure, Item>

    suspend fun addLastSeenItem(item: Item): Either<Failure, Boolean>
}