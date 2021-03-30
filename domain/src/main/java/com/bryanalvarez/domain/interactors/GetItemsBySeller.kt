package com.bryanalvarez.domain.interactors

import arrow.core.Either
import arrow.core.Failure
import arrow.core.Left
import arrow.core.Right
import com.bryanalvarez.domain.models.SellerInfo
import com.bryanalvarez.domain.repository.Repository

class GetItemsBySeller(private val repository: Repository):
    UseCase<SellerInfo, GetItemsBySeller.Params>() {

    data class Params(
        val sellerId: String?
    ) : UseCase.Input

    override suspend fun run(parameter: Params?): Either<Failure, SellerInfo> {
        repository.getItemsBySeller(parameter?.sellerId!!).fold(
            {
                return Left(it)
            },{
                return Right(it)
            }
        )
    }
}