package id.go.jabarprov.dbmpr.core_main.extensions

import id.go.jabarprov.dbmpr.core_main.either.Either
import id.go.jabarprov.dbmpr.core_main.exceptions.LocalDataSourceException
import id.go.jabarprov.dbmpr.core_main.exceptions.RemoteDataSourceException
import id.go.jabarprov.dbmpr.core_main.failures.Failure
import id.go.jabarprov.dbmpr.core_main.failures.LocalDataSourceFailure
import id.go.jabarprov.dbmpr.core_main.failures.RemoteDataSourceFailure

fun <R> R.toSuccess(): Either.Success<R> {
    return Either.Success(this)
}

fun <L> L.toError(): Either.Error<L> {
    return Either.Error(this)
}

suspend fun <T> safeDataSourceCall(dataSourceCall: suspend () -> T): Either<Failure, T> {
    return try {
        dataSourceCall().toSuccess()
    } catch (e: Exception) {
        return when (e) {
            is LocalDataSourceException -> LocalDataSourceFailure(
                e.message ?: "Gagal Mengambil Data Dari Local Data Source"
            ).toError()
            is RemoteDataSourceException -> RemoteDataSourceFailure(
                e.message ?: "Gagal Mengambil Data Dari Remote Data Source"
            ).toError()
            else -> throw e
        }
    }
}