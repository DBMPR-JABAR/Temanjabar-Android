package id.go.jabarprov.dbmpr.feature.news.presentation.viewmodels.store

import id.go.jabarprov.dbmpr.common.domain.entity.News
import id.go.jabarprov.dbmpr.core_main.Resource
import id.go.jabarprov.dbmpr.core_main.store.State

data class NewsState(
    val news: Resource<News> = Resource.Initial()
) : State