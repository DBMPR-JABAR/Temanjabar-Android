package id.go.jabarprov.dbmpr.common.domain.repository

import id.go.jabarprov.dbmpr.core_main.either.Either
import id.go.jabarprov.dbmpr.core_main.failures.Failure
import id.go.jabarprov.dbmpr.common.domain.entity.News

interface NewsRepository {
    suspend fun getNewsForSlider(): Either<Failure, List<News>>
    suspend fun getNews(slug: String): Either<Failure, News>
}