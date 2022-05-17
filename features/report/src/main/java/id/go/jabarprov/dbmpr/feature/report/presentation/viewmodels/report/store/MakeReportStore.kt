package id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.store

import android.net.Uri
import id.go.jabarprov.dbmpr.core_main.store.Store
import id.go.jabarprov.dbmpr.feature.report.presentation.models.CategoryReportModel
import id.go.jabarprov.dbmpr.feature.report.presentation.models.PhotoModel
import id.go.jabarprov.dbmpr.feature.report.presentation.models.VideoModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MakeReportStore @Inject constructor() :
    Store<MakeReportAction, MakeReportState>(MakeReportState()) {
    override fun reduce(action: MakeReportAction) {
        coroutineScope.launch {
            when (action) {
                is MakeReportAction.UpdateSearchKeyword -> updateListCategoryBySearchKeyword(action.keyword)
                is MakeReportAction.UpdateSelectedCategory -> updateSelectedCategory(action.category)
                is MakeReportAction.GoToNextScreen -> goToNextScreen()
                is MakeReportAction.GoToPreviousScreen -> goToPreviousScreen()
                is MakeReportAction.AddPhoto -> addPhoto(action.uri)
                is MakeReportAction.UpdateListPhoto -> updateListPhoto(action.listPhotoModel)
                is MakeReportAction.AddVideo -> addVideo(action.uri)
                MakeReportAction.ClearVideo -> clearVideo()
            }
        }
    }

    private fun updateListCategoryBySearchKeyword(keyword: String) {
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
            state.value.copy(
                currentListPhoto = state.value.currentListPhoto + listOf(
                    PhotoModel(
                        photoUri
                    )
                )
            )
    }

    private fun addVideo(videoUri: Uri) {
        state.value = state.value.copy(
            currentVideo = VideoModel(videoUri)
        )
    }

    private fun clearVideo() {
        state.value = state.value.copy(
            currentVideo = null
        )
    }

    private fun updateListPhoto(listPhotoModel: List<PhotoModel>) {
        state.value = state.value.copy(currentListPhoto = listPhotoModel)
    }
}