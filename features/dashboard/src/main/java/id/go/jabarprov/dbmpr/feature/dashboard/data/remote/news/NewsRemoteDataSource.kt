package id.go.jabarprov.dbmpr.feature.dashboard.data.remote.news

import id.go.jabarprov.dbmpr.feature.dashboard.data.models.remote.response.NewsResponse

interface NewsRemoteDataSource {
    suspend fun getNewsForSlider(): List<NewsResponse>
}