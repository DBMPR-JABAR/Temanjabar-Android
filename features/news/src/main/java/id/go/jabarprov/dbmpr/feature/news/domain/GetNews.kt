package id.go.jabarprov.dbmpr.feature.news.domain

import id.go.jabarprov.dbmpr.common.domain.entity.News
import id.go.jabarprov.dbmpr.common.domain.repository.NewsRepository
import id.go.jabarprov.dbmpr.core_main.either.Either
import id.go.jabarprov.dbmpr.core_main.failures.Failure
import id.go.jabarprov.dbmpr.core_main.usecase.UseCase
import javax.inject.Inject

class GetNews @Inject constructor(private val newsRepository: NewsRepository) :
    UseCase<News, GetNews.Params>() {
    data class Params(val slug: String)

    override suspend fun run(params: Params): Either<Failure, News> {
        return newsRepository.getNews(params.slug)
    }
}