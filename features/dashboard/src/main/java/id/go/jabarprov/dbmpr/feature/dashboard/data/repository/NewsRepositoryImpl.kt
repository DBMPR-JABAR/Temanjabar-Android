package id.go.jabarprov.dbmpr.feature.dashboard.data.repository

import id.go.jabarprov.dbmpr.core_main.either.Either
import id.go.jabarprov.dbmpr.core_main.extensions.safeDataSourceCall
import id.go.jabarprov.dbmpr.core_main.failures.Failure
import id.go.jabarprov.dbmpr.feature.dashboard.data.mapper.NewsDataMapper
import id.go.jabarprov.dbmpr.feature.dashboard.data.remote.news.NewsRemoteDataSource
import id.go.jabarprov.dbmpr.feature.dashboard.domain.entity.News
import id.go.jabarprov.dbmpr.feature.dashboard.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsRemoteDataSource: NewsRemoteDataSource) :
    NewsRepository {
    override suspend fun getNewsForSlider(): Either<Failure, List<News>> {
        return safeDataSourceCall {
            val result = newsRemoteDataSource.getNewsForSlider()
            NewsDataMapper.convertListOfNewsResponseToListOfEntity(result)
        }
    }
}