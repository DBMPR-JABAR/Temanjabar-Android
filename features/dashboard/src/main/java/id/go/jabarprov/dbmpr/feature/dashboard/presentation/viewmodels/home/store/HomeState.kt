package id.go.jabarprov.dbmpr.feature.dashboard.presentation.viewmodels.home.store

import id.go.jabarprov.dbmpr.core_main.Resource
import id.go.jabarprov.dbmpr.core_main.store.State
import id.go.jabarprov.dbmpr.common.domain.entity.News
import id.go.jabarprov.dbmpr.feature.dashboard.domain.entity.RuasJalan

data class HomeState(
    val listSlideNewsState: Resource<List<News>> = Resource.Initial(),
    val nearbyRuasJalanState: Resource<RuasJalan> = Resource.Initial(),
    val currentLat: Double? = null,
    val currentLong: Double? = null
) : State