package com.bryanalvarez.domain.interactors

import arrow.core.Either
import arrow.core.Failure
import arrow.core.Left
import arrow.core.Right
import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.domain.models.UserSearch
import com.bryanalvarez.domain.repository.Repository

class GetUserRecentSearch (private val repository: Repository):
    UseCase<List<UserSearch>, GetUserRecentSearch.Params>() {

    data class Params(
        val message: String
    ) : UseCase.Input

    override suspend fun run(parameter: Params?): Either<Failure, List<UserSearch>> {
        repository.getUserRecentSearch().fold(
            {
                return Left(it)
            },{
                return Right(it)
            }
        )
    }
}