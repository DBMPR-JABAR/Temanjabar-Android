package id.go.jabarprov.dbmpr.core_main.usecase

import id.go.jabarprov.dbmpr.core_main.either.Either
import id.go.jabarprov.dbmpr.core_main.failures.Failure
import kotlinx.coroutines.*

abstract class UseCase<out Type, in Params> {

    private val coroutineScope = CoroutineScope(CoroutineName("UseCaseCoroutine"))

    abstract suspend fun run(params: Params): Either<Failure, Type>

    fun execute(params: Params, onResult: (Either<Failure, Type>) -> Unit) {
        val job = coroutineScope.async(Dispatchers.IO) { run(params) }
        coroutineScope.launch(Dispatchers.Main) { onResult(job.await()) }
    }

}