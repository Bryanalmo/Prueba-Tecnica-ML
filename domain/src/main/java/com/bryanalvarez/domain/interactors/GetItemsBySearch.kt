package com.bryanalvarez.domain.interactors

import arrow.core.Either
import arrow.core.Failure
import arrow.core.Left
import arrow.core.Right
import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.domain.models.ItemsListInfo
import com.bryanalvarez.domain.repository.Repository

class GetItemsBySearch(private val repository: Repository):
    UseCase<ItemsListInfo, GetItemsBySearch.Params>() {

    data class Params(
        val search: String,
        val offset: Int? = 0
    ) : UseCase.Input

    override suspend fun run(parameter: Params?): Either<Failure, ItemsListInfo> {
        repository.getItemsBySearch(parameter?.search!!, parameter?.offset!!).fold(
            {
                return Left(it)
            },{
                return Right(it)
            }
        )
    }
}