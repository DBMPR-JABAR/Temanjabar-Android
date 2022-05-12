package id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.store

import android.net.Uri
import id.go.jabarprov.dbmpr.core_main.store.Action
import id.go.jabarprov.dbmpr.feature.report.presentation.models.CategoryReportModel
import id.go.jabarprov.dbmpr.feature.report.presentation.models.PhotoModel
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.list_photo.store.ListPhotoAction

sealed class MakeReportAction : Action {
    data class UpdateSelectedCategory(val category: CategoryReportModel) : MakeReportAction()
    data class UpdateSearchKeyword(val keyword: String) : MakeReportAction()
    object GoToNextScreen : MakeReportAction()
    object GoToPreviousScreen : MakeReportAction()
    data class AddPhoto(val uri: Uri) : MakeReportAction()
    data class UpdateListPhoto(val listPhotoModel: List<PhotoModel>) : MakeReportAction()
}