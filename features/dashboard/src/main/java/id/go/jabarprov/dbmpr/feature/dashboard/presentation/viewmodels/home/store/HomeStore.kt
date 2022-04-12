package id.go.jabarprov.dbmpr.feature.dashboard.presentation.viewmodels.home.store

import id.go.jabarprov.dbmpr.core_main.None
import id.go.jabarprov.dbmpr.core_main.Resource
import id.go.jabarprov.dbmpr.core_main.store.Store
import id.go.jabarprov.dbmpr.feature.dashboard.domain.usecase.GetNewsForSlider
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeStore @Inject constructor(private val getNewsForSlider: GetNewsForSlider) :
    Store<HomeAction, HomeState>(HomeState(listSlideNewsState = Resource.Loading())) {
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
            }
        }
    }
}