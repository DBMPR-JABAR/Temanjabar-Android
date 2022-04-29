package id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.store

import id.go.jabarprov.dbmpr.core_main.store.Action
import id.go.jabarprov.dbmpr.feature.report.presentation.models.CategoryReportModel

sealed class MakeReportAction : Action {
    data class UpdateSelectedCategory(val category: CategoryReportModel) : MakeReportAction()
    data class UpdateSearchKeyword(val keyword: String) : MakeReportAction()
    object GoToNextScreen : MakeReportAction()
    object GoToPreviousScreen : MakeReportAction()
}