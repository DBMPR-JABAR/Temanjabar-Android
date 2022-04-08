package id.go.jabarprov.dbmpr.core_main.store

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow

abstract class Store<A : Action, S : State>(initialState: S) {
    val state = MutableStateFlow(initialState)
    protected val coroutineScope = CoroutineScope(CoroutineName("StoreScope"))
    abstract fun reduce(action: A)
    fun cancel() {
        coroutineScope.cancel()
    }
}