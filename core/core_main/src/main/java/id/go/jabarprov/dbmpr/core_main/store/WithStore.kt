package id.go.jabarprov.dbmpr.core_main.store

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow

interface WithStore<T : Action, S : State> {
    val actionChannel: Channel<T>

    val uiState: StateFlow<S>

    fun processAction(action: T)

    fun subscribeActionChannel()

    fun onCleared()
}