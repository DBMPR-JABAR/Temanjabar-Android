package id.go.jabarprov.dbmpr.feature.news.presentation.viewmodels.store

import id.go.jabarprov.dbmpr.core_main.Resource
import id.go.jabarprov.dbmpr.core_main.store.Store
import id.go.jabarprov.dbmpr.feature.news.domain.GetNews
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsStore @Inject constructor(private val getNews: GetNews) :
    Store<NewsAction, NewsState>(NewsState()) {
    override fun reduce(action: NewsAction) {
        coroutineScope.launch {
            when (action) {
                is NewsAction.GetNews -> {
                    state.value = state.value.copy(
                        news = Resource.Loading()
                    )
                    val params = GetNews.Params(action.slug)
                    val result = getNews.run(params)
                    result.either(
                        fnL = { failure ->
                            state.value = state.value.copy(
                                news = Resource.Failed(failure.message)
                            )
                        },
                        fnR = { news ->
                            state.value = state.value.copy(
                                news = Resource.Success(news)
                            )
                        },
                    )
                }
            }
        }
    }
}