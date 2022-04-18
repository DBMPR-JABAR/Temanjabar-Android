package id.go.jabarprov.dbmpr.feature.news.presentation.viewmodels.store

import id.go.jabarprov.dbmpr.core_main.store.Action

sealed class NewsAction : Action {
    data class GetNews(val slug: String) : NewsAction()
}