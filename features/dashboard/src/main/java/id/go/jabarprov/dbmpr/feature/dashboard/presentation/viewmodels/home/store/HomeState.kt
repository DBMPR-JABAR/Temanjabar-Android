package id.go.jabarprov.dbmpr.feature.dashboard.presentation.viewmodels.home.store

import id.go.jabarprov.dbmpr.core_main.store.State
import id.go.jabarprov.dbmpr.feature.dashboard.domain.entity.News

data class HomeState(
    val isFailed: Boolean = false,
    val errorMessage: String = "",
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val listSliderNews: List<News>? = null
) : State