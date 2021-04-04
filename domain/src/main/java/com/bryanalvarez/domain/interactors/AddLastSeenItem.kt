package com.bryanalvarez.domain.interactors

import arrow.core.Either
import arrow.core.Failure
import arrow.core.Left
import arrow.core.Right
import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.domain.repository.Repository

class AddLastSeenItem (private val repository: Repository):
    UseCase<Boolean, AddLastSeenItem.Params>() {

    data class Params(
        val item: Item
    ) : UseCase.Input

    /**
     * override function to call the repository in the coroutine
     */
    override suspend fun run(parameter: Params?): Either<Failure, Boolean> {
        repository.addLastSeenItem(parameter?.item!!).fold(
            {
                return Left(it)
            },{
                return Right(it)
            }
        )
    }
}