package id.go.jabarprov.dbmpr.feature.dashboard.presentation.viewmodels.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.jabarprov.dbmpr.feature.dashboard.domain.usecase.GetNewsForSlider
import id.go.jabarprov.dbmpr.surveisapulubang.core.None
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(private val getNewsForSlider: GetNewsForSlider) :
    ViewModel() {

    fun getNewsForSlider() {
        viewModelScope.launch {
            val result = getNewsForSlider.run(None)
            result.either(
                fnL = { failure ->
                    Log.e(TAG, "getNewsForSlider: ERROR ${failure.message}")
                },
                fnR = { listNews ->
                    Log.d(TAG, "getNewsForSlider: $listNews")
                },
            )
        }
    }

}