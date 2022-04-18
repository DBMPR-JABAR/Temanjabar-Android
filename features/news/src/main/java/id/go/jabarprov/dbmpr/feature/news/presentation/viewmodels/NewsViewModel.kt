package id.go.jabarprov.dbmpr.feature.news.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.jabarprov.dbmpr.core_main.store.WithStore
import id.go.jabarprov.dbmpr.feature.news.presentation.viewmodels.store.NewsAction
import id.go.jabarprov.dbmpr.feature.news.presentation.viewmodels.store.NewsState
import id.go.jabarprov.dbmpr.feature.news.presentation.viewmodels.store.NewsStore
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val store: NewsStore) : ViewModel(),
    WithStore<NewsAction, NewsState> {

    override val actionChannel: Channel<NewsAction> = Channel()

    override val uiState: StateFlow<NewsState> = store.state.asStateFlow()

    init {
        subscribeActionChannel()
    }

    override fun processAction(action: NewsAction) {
        viewModelScope.launch {
            actionChannel.send(action)
        }
    }

    override fun subscribeActionChannel() {
        viewModelScope.launch {
            actionChannel
                .receiveAsFlow()
                .filterNotNull()
                .collect {
                    store.reduce(it)
                }
        }
    }
}