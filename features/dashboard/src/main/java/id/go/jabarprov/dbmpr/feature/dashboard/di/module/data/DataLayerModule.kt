package id.go.jabarprov.dbmpr.feature.dashboard.di.module.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import id.go.jabarprov.dbmpr.feature.dashboard.data.remote.news.NewsRemoteDataSource
import id.go.jabarprov.dbmpr.feature.dashboard.data.remote.news.NewsRemoteDataSourceImpl
import id.go.jabarprov.dbmpr.feature.dashboard.data.repository.NewsRepositoryImpl
import id.go.jabarprov.dbmpr.feature.dashboard.domain.repository.NewsRepository

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class DataLayerModule {
    @Binds
    abstract fun providesNewsRemoteDataSource(newsRemoteDataSourceImpl: NewsRemoteDataSourceImpl): NewsRemoteDataSource

    @Binds
    abstract fun providesNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}