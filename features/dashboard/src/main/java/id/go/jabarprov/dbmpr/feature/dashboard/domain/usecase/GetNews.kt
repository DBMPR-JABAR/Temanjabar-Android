package id.go.jabarprov.dbmpr.feature.dashboard.domain.usecase

import id.go.jabarprov.dbmpr.core_main.either.Either
import id.go.jabarprov.dbmpr.core_main.failures.Failure
import id.go.jabarprov.dbmpr.core_main.usecase.UseCase
import id.go.jabarprov.dbmpr.common.domain.entity.News

class GetNews : UseCase<News, GetNews.Params>() {
    data class Params(val slug: String)

    override suspend fun run(params: Params): Either<Failure, News> {
        TODO("Not yet implemented")
    }
}