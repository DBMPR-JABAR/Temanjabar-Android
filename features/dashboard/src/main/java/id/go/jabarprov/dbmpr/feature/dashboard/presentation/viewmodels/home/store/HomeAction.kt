package id.go.jabarprov.dbmpr.feature.dashboard.presentation.viewmodels.home.store

import id.go.jabarprov.dbmpr.core_main.store.Action

sealed class HomeAction : Action {
    object GetSliderNews : HomeAction()
    data class GetNearbyRuasJalan(val lat: Double, val long: Double) : HomeAction()
}