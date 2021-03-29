package com.bryanalvarez.domain.interactors

import arrow.core.Either
import arrow.core.Failure
import arrow.core.Left
import arrow.core.Right
import com.bryanalvarez.domain.models.UserSearch
import com.bryanalvarez.domain.repository.Repository

class AddUserSearch  (private val repository: Repository):
    UseCase<Boolean, AddUserSearch.Params>() {

    data class Params(
        val search: String
    ) : UseCase.Input

    override suspend fun run(parameter: Params?): Either<Failure, Boolean> {
        repository.addUserSearch(parameter?.search!!).fold(
            {
                return Left(it)
            },{
                return Right(it)
            }
        )
    }
}