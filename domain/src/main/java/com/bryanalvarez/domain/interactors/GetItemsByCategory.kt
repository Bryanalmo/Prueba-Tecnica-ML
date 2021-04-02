package com.bryanalvarez.domain.interactors

import arrow.core.Either
import arrow.core.Failure
import arrow.core.Left
import arrow.core.Right
import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.domain.models.ItemsListInfo
import com.bryanalvarez.domain.repository.Repository

class GetItemsByCategory (private val repository: Repository):
    UseCase<ItemsListInfo, GetItemsByCategory.Params>() {

    data class Params(
        val categoryId: String,
        val offset: Int? = 0
    ) : UseCase.Input

    override suspend fun run(parameter: Params?): Either<Failure, ItemsListInfo> {
        repository.getItemsByCategory(parameter?.categoryId!!, parameter?.offset!!).fold(
            {
                return Left(it)
            },{
                return Right(it)
            }
        )
    }
}