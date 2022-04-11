package id.go.jabarprov.dbmpr.feature.dashboard.data.services

import data.models.remote.response.BaseResponse
import id.go.jabarprov.dbmpr.feature.dashboard.data.models.remote.response.NewsResponse
import retrofit2.Response
import retrofit2.http.GET

interface NewsAPI {
    @GET("news/for-slider")
    suspend fun getNewsForSlider(): Response<BaseResponse<List<NewsResponse>>>
}