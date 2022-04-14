package id.go.jabarprov.dbmpr.common.di.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import id.go.jabarprov.dbmpr.common.data.datasource.remote.news.NewsRemoteDataSource
import id.go.jabarprov.dbmpr.common.data.datasource.remote.news.NewsRemoteDataSourceImpl
import id.go.jabarprov.dbmpr.common.data.repository.NewsRepositoryImpl
import id.go.jabarprov.dbmpr.common.domain.repository.NewsRepository

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class DataLayerModule {
    @Binds
    abstract fun providesNewsRemoteDataSource(newsRemoteDataSourceImpl: NewsRemoteDataSourceImpl): NewsRemoteDataSource

    @Binds
    abstract fun providesNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}