package com.bryanalvarez.domain.interactors

import arrow.core.Either
import arrow.core.Failure
import arrow.core.Left
import arrow.core.Right
import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.domain.repository.Repository

class GetLastSeenItem (private val repository: Repository):
    UseCase<Item, GetLastSeenItem.Params>() {

    data class Params(
        val param: Any
    ) : UseCase.Input

    /**
     * override function to call the repository in the coroutine
     */
    override suspend fun run(parameter: Params?): Either<Failure, Item> {
        repository.getLastSeenItem().fold(
            {
                return Left(it)
            },{
                return Right(it)
            }
        )
    }
}