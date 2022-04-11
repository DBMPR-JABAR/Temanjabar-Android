package id.go.jabarprov.dbmpr.feature.dashboard.domain.repository

import id.go.jabarprov.dbmpr.core_main.either.Either
import id.go.jabarprov.dbmpr.core_main.failures.Failure
import id.go.jabarprov.dbmpr.feature.dashboard.domain.entity.News

interface NewsRepository {
    suspend fun getNewsForSlider(): Either<Failure, List<News>>
}