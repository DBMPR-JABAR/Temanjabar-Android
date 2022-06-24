package id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.store

import android.net.Uri
import id.go.jabarprov.dbmpr.core_main.store.Action
import id.go.jabarprov.dbmpr.feature.report.presentation.models.CategoryReportModel
import id.go.jabarprov.dbmpr.feature.report.presentation.models.PhotoModel

sealed class FormReportAction : Action {
    data class UpdateSelectedCategory(val category: CategoryReportModel) : FormReportAction()
    data class UpdateSearchKeyword(val keyword: String) : FormReportAction()
    object GoToNextScreen : FormReportAction()
    object GoToPreviousScreen : FormReportAction()
    data class AddPhoto(val uri: Uri) : FormReportAction()
    data class AddVideo(val uri: Uri) : FormReportAction()
    object ClearVideo : FormReportAction()
    data class UpdateListPhoto(val listPhotoModel: List<PhotoModel>) : FormReportAction()
    data class UpdateDescription(val description: String?) : FormReportAction()
    data class UpdateLocation(val location: String?) : FormReportAction()
    data class UpdateExplanation(val explanation: String?) : FormReportAction()
    data class UpdateReportPrivacy(val reportPrivacy: MakeReportPrivacy) : FormReportAction()
}