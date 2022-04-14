package id.go.jabarprov.dbmpr.common.di.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import id.go.jabarprov.dbmpr.common.data.services.NewsAPI
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class NetworkModule {
    companion object {
        @Provides
        fun providesNewsApi(retrofit: Retrofit): NewsAPI {
            return retrofit.create(NewsAPI::class.java)
        }
    }
}