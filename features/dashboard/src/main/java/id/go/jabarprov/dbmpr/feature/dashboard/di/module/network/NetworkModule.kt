package id.go.jabarprov.dbmpr.feature.dashboard.di.module.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import id.go.jabarprov.dbmpr.feature.dashboard.data.services.NewsAPI
import id.go.jabarprov.dbmpr.feature.dashboard.data.services.RuasJalanAPI
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class NetworkModule {
    companion object {
        @Provides
        fun providesNewsApi(retrofit: Retrofit): NewsAPI {
            return retrofit.create(NewsAPI::class.java)
        }

        @Provides
        fun providesRuasJalanApi(retrofit: Retrofit): RuasJalanAPI {
            return retrofit.create(RuasJalanAPI::class.java)
        }
    }
}