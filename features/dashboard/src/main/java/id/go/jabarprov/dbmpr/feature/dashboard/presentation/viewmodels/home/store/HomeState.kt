package id.go.jabarprov.dbmpr.feature.dashboard.presentation.viewmodels.home.store

import id.go.jabarprov.dbmpr.core_main.Resource
import id.go.jabarprov.dbmpr.core_main.store.State
import id.go.jabarprov.dbmpr.feature.dashboard.domain.entity.News
import id.go.jabarprov.dbmpr.feature.dashboard.domain.entity.RuasJalan

data class HomeState(
    val listSlideNewsState: Resource<List<News>> = Resource.Loading(),
    val nearbyRuasJalanState: Resource<RuasJalan> = Resource.Loading()
) : State