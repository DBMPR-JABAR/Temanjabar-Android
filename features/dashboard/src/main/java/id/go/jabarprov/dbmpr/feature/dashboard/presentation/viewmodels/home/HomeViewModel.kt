package id.go.jabarprov.dbmpr.feature.dashboard.presentation.viewmodels.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.jabarprov.dbmpr.core_main.store.WithStore
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.viewmodels.home.store.HomeAction
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.viewmodels.home.store.HomeState
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.viewmodels.home.store.HomeStore
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeStore: HomeStore) :
    ViewModel(), WithStore<HomeAction, HomeState> {

    override val actionChannel: Channel<HomeAction> = Channel()

    override val uiState: StateFlow<HomeState> = homeStore.state.asStateFlow()

    init {
        subscribeActionChannel()
    }

    override fun processAction(action: HomeAction) {
        viewModelScope.launch {
            homeStore.reduce(action)
        }
    }

    override fun subscribeActionChannel() {
        viewModelScope.launch {
            actionChannel
                .receiveAsFlow()
                .filterNotNull()
                .collect {
                    processAction(it)
                }
        }
    }

}