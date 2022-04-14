package id.go.jabarprov.dbmpr.common.data.repository

import id.go.jabarprov.dbmpr.common.data.datasource.remote.news.NewsRemoteDataSource
import id.go.jabarprov.dbmpr.core_main.either.Either
import id.go.jabarprov.dbmpr.core_main.extensions.safeDataSourceCall
import id.go.jabarprov.dbmpr.core_main.failures.Failure
import id.go.jabarprov.dbmpr.common.data.mapper.NewsDataMapper
import id.go.jabarprov.dbmpr.common.domain.entity.News
import id.go.jabarprov.dbmpr.common.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsRemoteDataSource: NewsRemoteDataSource) :
    NewsRepository {
    override suspend fun getNewsForSlider(): Either<Failure, List<News>> {
        return safeDataSourceCall {
            val result = newsRemoteDataSource.getNewsForSlider()
            NewsDataMapper.convertListOfNewsResponseToListOfEntity(result)
        }
    }

    override suspend fun getNews(slug: String): Either<Failure, News> {
        return safeDataSourceCall {
            val result = newsRemoteDataSource.getNews(slug)
            NewsDataMapper.convertNewsResponseToEntity(result)
        }
    }
}