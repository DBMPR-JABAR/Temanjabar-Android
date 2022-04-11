package id.go.jabarprov.dbmpr.feature.dashboard.data.remote.news

import functions.safeApiCall
import id.go.jabarprov.dbmpr.feature.dashboard.data.models.remote.response.NewsResponse
import id.go.jabarprov.dbmpr.feature.dashboard.data.services.NewsAPI
import javax.inject.Inject


class NewsRemoteDataSourceImpl @Inject constructor(private val newsAPI: NewsAPI) :
    NewsRemoteDataSource {
    override suspend fun getNewsForSlider(): List<NewsResponse> {
        return safeApiCall {
            val response = newsAPI.getNewsForSlider()
            response.body()?.data!!
        }
    }
}