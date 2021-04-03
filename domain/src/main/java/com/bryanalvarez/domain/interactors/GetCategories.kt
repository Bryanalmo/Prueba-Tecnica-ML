package com.bryanalvarez.domain.interactors

import arrow.core.Either
import arrow.core.Failure
import arrow.core.Left
import arrow.core.Right
import com.bryanalvarez.domain.models.Category
import com.bryanalvarez.domain.repository.Repository

class GetCategories(private val repository: Repository):
    UseCase<List<Category>, GetCategories.Params>() {

    data class Params(
        val param: Any
    ) : UseCase.Input

    /**
     * override function to call the repository in the coroutine
     */
    override suspend fun run(parameter: Params?): Either<Failure, List<Category>> {
        repository.getCategories().fold(
            {
                return Left(it)
            },{
                return Right(it)
            }
        )
    }
}