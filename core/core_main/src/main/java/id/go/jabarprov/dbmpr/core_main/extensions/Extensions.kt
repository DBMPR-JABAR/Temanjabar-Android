package id.go.jabarprov.dbmpr.core_main.extensions

import id.go.jabarprov.dbmpr.core_main.either.Either

fun <R> R.toSuccess(): Either.Success<R> {
    return Either.Success(this)
}

fun <L> L.toError(): Either.Error<L> {
    return Either.Error(this)
}