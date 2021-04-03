package com.bryanalvarez.domain.interactors

import arrow.core.Either
import arrow.core.Failure
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class UseCase<T, I: UseCase.Input>() {

    /**
     * function to launch run function inside another threat using coroutines
     */
    fun execute(parameter: I?,
                block: (Either<Failure, T>) -> Unit
    ) {

        GlobalScope.launch(Dispatchers.Main) {
            val job = async(Dispatchers.IO) {
                run(parameter)
            }
            block(job.await())
        }
    }

    protected abstract suspend fun run(parameter: I?): Either<Failure, T>

    interface Input

}
