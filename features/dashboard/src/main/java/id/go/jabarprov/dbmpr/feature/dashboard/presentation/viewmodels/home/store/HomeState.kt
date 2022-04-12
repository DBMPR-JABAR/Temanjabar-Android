package id.go.jabarprov.dbmpr.feature.dashboard.presentation.viewmodels.home.store

import id.go.jabarprov.dbmpr.core_main.Resource
import id.go.jabarprov.dbmpr.core_main.store.State
import id.go.jabarprov.dbmpr.feature.dashboard.domain.entity.News

data class HomeState(
    val listSlideNewsState: Resource<List<News>>
) : State