package id.go.jabarprov.dbmpr.feature.dashboard.presentation.viewmodels.home.store

import id.go.jabarprov.dbmpr.core_main.None
import id.go.jabarprov.dbmpr.core_main.Resource
import id.go.jabarprov.dbmpr.core_main.store.Store
import id.go.jabarprov.dbmpr.feature.dashboard.domain.usecase.GetNearbyRuasJalan
import id.go.jabarprov.dbmpr.feature.dashboard.domain.usecase.GetNewsForSlider
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeStore @Inject constructor(
    private val getNewsForSlider: GetNewsForSlider,
    private val getNearbyRuasJalan: GetNearbyRuasJalan
) :
    Store<HomeAction, HomeState>(HomeState()) {
    override fun reduce(action: HomeAction) {
        coroutineScope.launch {
            when (action) {
                HomeAction.GetSliderNews -> {
                    state.value = state.value.copy(
                        listSlideNewsState = Resource.Loading()
                    )
                    val result = getNewsForSlider.run(None)
                    result.either(
                        fnL = { failure ->
                            state.value = state.value.copy(
                                listSlideNewsState = Resource.Failed(failure.message)
                            )
                        },
                        fnR = { listNews ->
                            state.value = state.value.copy(
                                listSlideNewsState = Resource.Success(listNews)
                            )
                        },
                    )
                }
                is HomeAction.GetNearbyRuasJalan -> {
                    state.value = state.value.copy(
                        nearbyRuasJalanState = Resource.Loading(),
                        currentLat = action.lat,
                        currentLong = action.long
                    )
                    val params = GetNearbyRuasJalan.Params(action.lat, action.long)
                    val result = getNearbyRuasJalan.run(params)
                    result.either(
                        fnL = { failure ->
                            state.value = state.value.copy(
                                nearbyRuasJalanState = Resource.Failed(failure.message)
                            )
                        },
                        fnR = { ruasJalan ->
                            state.value = state.value.copy(
                                nearbyRuasJalanState = Resource.Success(ruasJalan)
                            )
                        },
                    )
                }
                is HomeAction.GetLocation -> state.value = state.value.copy(
                    nearbyRuasJalanState = Resource.Loading()
                )
                is HomeAction.GetLocationFailed -> state.value = state.value.copy(
                    nearbyRuasJalanState = Resource.Failed(action.errorMessage)
                )
            }
        }
    }
}