package id.go.jabarprov.dbmpr.common.data.services

import id.go.jabarprov.dbmpr.common.data.models.remote.response.BaseResponse
import id.go.jabarprov.dbmpr.common.data.models.remote.response.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsAPI {
    @GET("news/for-slider")
    suspend fun getNewsForSlider(): Response<BaseResponse<List<NewsResponse>>>

    @GET("news/show/{slug}")
    suspend fun getNews(@Path("slug") slug: String): Response<BaseResponse<NewsResponse>>
}