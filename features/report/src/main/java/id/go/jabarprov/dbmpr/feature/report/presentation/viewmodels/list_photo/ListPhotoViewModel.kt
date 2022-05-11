package id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.list_photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.jabarprov.dbmpr.core_main.store.WithStore
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.list_photo.store.ListPhotoAction
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.list_photo.store.ListPhotoState
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.list_photo.store.ListPhotoStore
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPhotoViewModel @Inject constructor(private val store: ListPhotoStore) : ViewModel(),
    WithStore<ListPhotoAction, ListPhotoState> {

    override val actionChannel: Channel<ListPhotoAction> = Channel()

    override val uiState: StateFlow<ListPhotoState> = store.state.asStateFlow()

    init {
        subscribeActionChannel()
    }

    override fun processAction(action: ListPhotoAction) {
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