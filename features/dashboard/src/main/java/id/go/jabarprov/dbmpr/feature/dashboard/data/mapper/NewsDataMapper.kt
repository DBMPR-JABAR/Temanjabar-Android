package id.go.jabarprov.dbmpr.feature.dashboard.data.mapper

import id.go.jabarprov.dbmpr.feature.dashboard.data.models.remote.response.NewsResponse
import id.go.jabarprov.dbmpr.feature.dashboard.domain.entity.News

abstract class NewsDataMapper {
    companion object {
        fun convertNewsResponseToEntity(newsResponse: NewsResponse): News {
            return News(
                id = newsResponse.id,
                title = newsResponse.title,
                content = newsResponse.content,
                imageUrl = newsResponse.pathUrl
            )
        }

        fun convertListOfNewsResponseToListOfEntity(listNewsResponse: List<NewsResponse>): List<News> {
            return listNewsResponse.map { convertNewsResponseToEntity(it) }
        }
    }
}