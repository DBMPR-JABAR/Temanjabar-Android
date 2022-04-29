package id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.jabarprov.dbmpr.core_main.store.WithStore
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.store.MakeReportAction
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.store.MakeReportState
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.store.MakeReportStore
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MakeReportViewModel @Inject constructor(private val store: MakeReportStore) : ViewModel(),
    WithStore<MakeReportAction, MakeReportState> {

    override val actionChannel: Channel<MakeReportAction> = Channel()

    override val uiState: StateFlow<MakeReportState> = store.state.asStateFlow()

    init {
        subscribeActionChannel()
    }

    override fun processAction(action: MakeReportAction) {
        viewModelScope.launch {
            actionChannel.send(action)
        }
    }

    override fun subscribeActionChannel() {
        viewModelScope.launch {
            actionChannel.receiveAsFlow()
                .filterNotNull()
                .collect {
                    store.reduce(it)
                }
        }
    }
}