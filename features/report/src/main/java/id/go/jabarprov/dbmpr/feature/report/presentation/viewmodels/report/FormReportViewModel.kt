package id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.jabarprov.dbmpr.core_main.store.WithStore
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.store.FormReportAction
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.store.MakeReportState
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.store.FormReportStore
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormReportViewModel @Inject constructor(private val store: FormReportStore) : ViewModel(),
    WithStore<FormReportAction, MakeReportState> {

    override val actionChannel: Channel<FormReportAction> = Channel()

    override val uiState: StateFlow<MakeReportState> = store.state.asStateFlow()

    init {
        subscribeActionChannel()
    }

    override fun processAction(action: FormReportAction) {
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