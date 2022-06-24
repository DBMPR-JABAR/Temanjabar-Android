package id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.store

import android.net.Uri
import id.go.jabarprov.dbmpr.core_main.store.Store
import id.go.jabarprov.dbmpr.feature.report.presentation.models.CategoryReportModel
import id.go.jabarprov.dbmpr.feature.report.presentation.models.PhotoModel
import id.go.jabarprov.dbmpr.feature.report.presentation.models.VideoModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class FormReportStore @Inject constructor() :
    Store<FormReportAction, MakeReportState>(MakeReportState()) {
    override fun reduce(action: FormReportAction) {
        coroutineScope.launch {
            when (action) {
                is FormReportAction.UpdateSearchKeyword -> updateListCategoryBySearchKeyword(action.keyword)
                is FormReportAction.UpdateSelectedCategory -> updateSelectedCategory(action.category)
                is FormReportAction.GoToNextScreen -> goToNextScreen()
                is FormReportAction.GoToPreviousScreen -> goToPreviousScreen()
                is FormReportAction.AddPhoto -> addPhoto(action.uri)
                is FormReportAction.UpdateListPhoto -> updateListPhoto(action.listPhotoModel)
                is FormReportAction.AddVideo -> addVideo(action.uri)
                FormReportAction.ClearVideo -> clearVideo()
                is FormReportAction.UpdateDescription -> updateDescription(action.description)
                is FormReportAction.UpdateExplanation -> updateExplanation(action.explanation)
                is FormReportAction.UpdateLocation -> updateLocation(action.location)
                is FormReportAction.UpdateReportPrivacy -> updateReportPrivacy(action.reportPrivacy)
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
            MakeReportScreenState.PRIVACY -> {
                state.value = state.value.copy(screenState = MakeReportScreenState.CATEGORY)
            }
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
            MakeReportScreenState.CATEGORY -> {
                state.value = state.value.copy(screenState = MakeReportScreenState.PRIVACY)
            }
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

    private fun updateDescription(description: String?) {
        if (description.isNullOrEmpty()) {
            state.value =
                state.value.copy(descriptionErrorMessage = "Deskripsi laporan tidak boleh kosong!")
        } else {
            state.value = state.value.copy(descriptionErrorMessage = null)
        }
        state.value = state.value.copy(description = description)
    }

    private fun updateLocation(location: String?) {
        if (location.isNullOrEmpty()) {
            state.value =
                state.value.copy(locationErrorMessage = "Lokasi laporan tidak boleh kosong!")
        } else {
            state.value =
                state.value.copy(locationErrorMessage = null)
        }
        state.value = state.value.copy(location = location)
    }

    private fun updateExplanation(explanation: String?) {
        state.value = state.value.copy(explanation = explanation)
    }

    private fun updateReportPrivacy(privacy: MakeReportPrivacy) {
        state.value = state.value.copy(reportPrivacy = privacy)
    }
}