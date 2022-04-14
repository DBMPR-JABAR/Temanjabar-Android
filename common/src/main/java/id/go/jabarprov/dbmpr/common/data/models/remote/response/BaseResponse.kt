package id.go.jabarprov.dbmpr.common.data.models.remote.response

data class BaseResponse<T>(
    val success: String,
    val message: String,
    val data: T
)
