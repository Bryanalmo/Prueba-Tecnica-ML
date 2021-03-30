package com.bryanalvarez.domain.repository

import arrow.core.Either
import arrow.core.Failure
import com.bryanalvarez.domain.models.Category
import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.domain.models.SellerInfo
import com.bryanalvarez.domain.models.UserSearch

interface Repository {
    suspend fun getItemsBySearch(text: String): Either<Failure, List<Item>>

    suspend fun getUserRecentSearch(): Either<Failure, List<UserSearch>>

    suspend fun addUserSearch(text: String): Either<Failure, Boolean>

    suspend fun getCategories(): Either<Failure, List<Category>>

    suspend fun getItemsByCategory(categoryId: String): Either<Failure, List<Item>>

    suspend fun getItemsBySeller(sellerId: String): Either<Failure, SellerInfo>
}