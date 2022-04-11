package functions

import id.go.jabarprov.dbmpr.core_main.exceptions.RemoteDataSourceException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): T {
    return try {
        apiCall.invoke()
    } catch (throwable: Throwable) {
        throw when (throwable) {
            is UnknownHostException -> RemoteDataSourceException("Tidak Dapat Menghubungi Server")
            is SocketTimeoutException -> RemoteDataSourceException("Respon Dari Server Terlalu Lama")
            is IOException -> RemoteDataSourceException(throwable.message)
            is HttpException -> {
                if (throwable.code() >= 500) {
                    throw RemoteDataSourceException("Terjadi Kesalahan Pada Server")
                }
                RemoteDataSourceException(throwable.message)
            }
            else -> RemoteDataSourceException(throwable.message)
        }
    }
}