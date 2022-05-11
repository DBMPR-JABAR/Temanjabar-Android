package id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.store

import android.net.Uri
import id.go.jabarprov.dbmpr.core_main.store.Store
import id.go.jabarprov.dbmpr.feature.report.presentation.models.CategoryReportModel
import id.go.jabarprov.dbmpr.feature.report.presentation.models.PhotoModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MakeReportStore @Inject constructor() :
    Store<MakeReportAction, MakeReportState>(MakeReportState()) {
    override fun reduce(action: MakeReportAction) {
        coroutineScope.launch {
            when (action) {
                is MakeReportAction.UpdateSearchKeyword -> updateListBySearchKeyword(action.keyword)
                is MakeReportAction.UpdateSelectedCategory -> updateSelectedCategory(action.category)
                is MakeReportAction.GoToNextScreen -> goToNextScreen()
                is MakeReportAction.GoToPreviousScreen -> goToPreviousScreen()
                is MakeReportAction.AddPhoto -> addPhoto(action.uri)
            }
        }
    }

    private fun updateListBySearchKeyword(keyword: String) {
        state.value = state.value.copy(
            currentListCategoryReport = if (keyword.isEmpty()) {
                MakeReportState.LIST_CATEGORY_REPORT
                    .map {
                        it.copy(isSelected = it.id == state.value.selectedCategory?.id)
                    }
            } else {
                MakeReportState.LIST_CATEGORY_REPORT
                    .map {
                        it.copy(isSelected = it.id == state.value.selectedCategory?.id)
                    }
                    .filter {
                        it.description.contains(keyword, ignoreCase = true)
                    }
            }
        )
    }

    private fun updateSelectedCategory(category: CategoryReportModel) {
        state.value = state.value.copy(
            selectedCategory = category,
            currentListCategoryReport = state.value.currentListCategoryReport.map {
                it.copy(isSelected = it.id == category.id)
            }
        )
    }

    private fun goToNextScreen() {
        when (state.value.screenState) {
            MakeReportScreenState.CATEGORY -> {
                state.value = state.value.copy(screenState = MakeReportScreenState.PHOTO_VIDEO)
            }
            MakeReportScreenState.PHOTO_VIDEO -> {
                state.value = state.value.copy(screenState = MakeReportScreenState.DETAIL)
            }
            else -> Unit
        }
    }

    private fun goToPreviousScreen() {
        when (state.value.screenState) {
            MakeReportScreenState.DETAIL -> {
                state.value = state.value.copy(screenState = MakeReportScreenState.PHOTO_VIDEO)
            }
            MakeReportScreenState.PHOTO_VIDEO -> {
                state.value = state.value.copy(screenState = MakeReportScreenState.CATEGORY)
            }
            else -> Unit
        }
    }

    private fun addPhoto(photoUri: Uri) {
        state.value =
            state.value.copy(currentListPhoto = state.value.currentListPhoto + listOf(PhotoModel(photoUri)))
    }
}