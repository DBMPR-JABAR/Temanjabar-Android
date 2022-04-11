package id.go.jabarprov.dbmpr.feature.dashboard.domain.usecase

import id.go.jabarprov.dbmpr.core_main.either.Either
import id.go.jabarprov.dbmpr.core_main.failures.Failure
import id.go.jabarprov.dbmpr.core_main.usecase.UseCase
import id.go.jabarprov.dbmpr.feature.dashboard.domain.entity.News
import id.go.jabarprov.dbmpr.feature.dashboard.domain.repository.NewsRepository
import id.go.jabarprov.dbmpr.core_main.None
import javax.inject.Inject

class GetNewsForSlider @Inject constructor(private val newsRepository: NewsRepository) :
    UseCase<List<News>, None>() {
    override suspend fun run(params: None): Either<Failure, List<News>> {
        return newsRepository.getNewsForSlider()
    }
}