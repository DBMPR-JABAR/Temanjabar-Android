package id.go.jabarprov.dbmpr.common.data.mapper

import id.go.jabarprov.dbmpr.common.data.models.remote.response.NewsResponse
import id.go.jabarprov.dbmpr.common.domain.entity.News
import id.go.jabarprov.dbmpr.utils.utils.CalendarUtils


abstract class NewsDataMapper {
    companion object {
        fun convertNewsResponseToEntity(newsResponse: NewsResponse): News {
            return News(
                id = newsResponse.id,
                title = newsResponse.title,
                content = newsResponse.content,
                imageUrl = newsResponse.pathUrl,
                slug = newsResponse.slug,
                publishedAt = newsResponse.publishedAt.let {
                    CalendarUtils.formatStringToCalendar(
                        newsResponse.publishedAt,
                        CalendarUtils.ISO_8601
                    )
                },
                publishedBy = newsResponse.publisher?.name
            )
        }

        fun convertListOfNewsResponseToListOfEntity(listNewsResponse: List<NewsResponse>): List<News> {
            return listNewsResponse.map { convertNewsResponseToEntity(it) }
        }
    }
}