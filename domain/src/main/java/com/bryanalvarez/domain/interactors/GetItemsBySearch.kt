package com.bryanalvarez.domain.interactors

import arrow.core.Either
import arrow.core.Failure
import arrow.core.Left
import arrow.core.Right
import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.domain.repository.Repository

class GetItemsBySearch(private val repository: Repository):
    UseCase<List<Item>, GetItemsBySearch.Params>() {

    data class Params(
        val search: String
    ) : UseCase.Input

    override suspend fun run(parameter: Params?): Either<Failure, List<Item>> {
        repository.getItemsBySearch(parameter?.search!!).fold(
            {
                return Left(it)
            },{
                return Right(it)
            }
        )
    }
}