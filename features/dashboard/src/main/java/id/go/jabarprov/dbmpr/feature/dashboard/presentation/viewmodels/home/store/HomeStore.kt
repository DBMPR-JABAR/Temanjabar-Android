package id.go.jabarprov.dbmpr.feature.dashboard.presentation.viewmodels.home.store

import id.go.jabarprov.dbmpr.core_main.store.Store
import id.go.jabarprov.dbmpr.feature.dashboard.domain.usecase.GetNewsForSlider
import id.go.jabarprov.dbmpr.core_main.None
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeStore @Inject constructor(private val getNewsForSlider: GetNewsForSlider) :
    Store<HomeAction, HomeState>(HomeState()) {
    override fun reduce(action: HomeAction) {
        coroutineScope.launch {
            when (action) {
                HomeAction.GetSliderNews -> {
                    state.value = state.value.copy(
                        isLoading = true,
                        isFailed = false,
                        errorMessage = "",
                        isSuccess = false
                    )
                    val result = getNewsForSlider.run(None)
                    result.either(
                        fnL = { failure ->
                            state.value = state.value.copy(
                                isLoading = false,
                                isFailed = true,
                                errorMessage = failure.message,
                                isSuccess = false
                            )
                        },
                        fnR = { listNews ->
                            state.value = state.value.copy(
                                isLoading = false,
                                isFailed = false,
                                errorMessage = "",
                                isSuccess = true,
                                listSliderNews = listNews
                            )
                        },
                    )
                }
            }
        }
    }
}