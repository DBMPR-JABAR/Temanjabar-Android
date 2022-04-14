package id.go.jabarprov.dbmpr.common.data.datasource.remote.news

import id.go.jabarprov.dbmpr.common.data.models.remote.response.NewsResponse

interface NewsRemoteDataSource {
    suspend fun getNewsForSlider(): List<NewsResponse>
    suspend fun getNews(slug: String): NewsResponse
}