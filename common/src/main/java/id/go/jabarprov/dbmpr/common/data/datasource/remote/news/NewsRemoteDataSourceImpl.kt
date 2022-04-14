package id.go.jabarprov.dbmpr.common.data.datasource.remote.news

import id.go.jabarprov.dbmpr.common.data.models.remote.response.NewsResponse
import id.go.jabarprov.dbmpr.common.data.services.NewsAPI
import id.go.jabarprov.dbmpr.common.functions.safeApiCall
import javax.inject.Inject

class NewsRemoteDataSourceImpl @Inject constructor(private val newsApi: NewsAPI) :
    NewsRemoteDataSource {
    override suspend fun getNewsForSlider(): List<NewsResponse> {
        return safeApiCall {
            val response = newsApi.getNewsForSlider()
            response.body()?.data!!
        }
    }

    override suspend fun getNews(slug: String): NewsResponse {
        return safeApiCall {
            val response = newsApi.getNews(slug)
            response.body()?.data!!
        }
    }
}