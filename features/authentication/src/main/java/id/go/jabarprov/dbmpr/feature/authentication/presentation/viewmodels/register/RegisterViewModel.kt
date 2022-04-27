package id.go.jabarprov.dbmpr.feature.authentication.presentation.viewmodels.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.jabarprov.dbmpr.core_main.store.WithStore
import id.go.jabarprov.dbmpr.feature.authentication.presentation.viewmodels.register.store.RegisterAction
import id.go.jabarprov.dbmpr.feature.authentication.presentation.viewmodels.register.store.RegisterState
import id.go.jabarprov.dbmpr.feature.authentication.presentation.viewmodels.register.store.RegisterStore
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val store: RegisterStore) : ViewModel(),
    WithStore<RegisterAction, RegisterState> {

    override val actionChannel: Channel<RegisterAction> = Channel()

    override val uiState: StateFlow<RegisterState> = store.state.asStateFlow()

    init {
        subscribeActionChannel()
    }

    override fun processAction(action: RegisterAction) {
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

    override fun onCleared() {
        super.onCleared()
        store.cancel()
    }
}