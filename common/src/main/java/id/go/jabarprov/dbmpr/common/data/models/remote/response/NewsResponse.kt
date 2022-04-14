package id.go.jabarprov.dbmpr.common.data.models.remote.response

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    val id: Int,
    val title: String,
    val slug: String,
    @SerializedName("path_url") val pathUrl: String,
    val description: String,
    val content: String,
    @SerializedName("published_at") val publishedAt: String,
    val userId: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    val publisher: PublisherResponse?
)
