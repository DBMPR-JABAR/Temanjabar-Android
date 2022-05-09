package id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.store

import android.net.Uri
import id.go.jabarprov.dbmpr.core_main.store.State
import id.go.jabarprov.dbmpr.feature.report.presentation.models.CategoryReportModel
import id.go.jabarprov.dbmpr.feature.report.presentation.models.PhotoModel

data class MakeReportState(
    val currentList: List<CategoryReportModel> = LIST_CATEGORY_REPORT,
    val selectedCategory: CategoryReportModel? = null,
    val screenState: MakeReportScreenState = MakeReportScreenState.CATEGORY,
    val listPhoto: List<PhotoModel> = listOf()
) : State {
    companion object {
        val LIST_CATEGORY_REPORT = listOf(
            CategoryReportModel(
                0,
                id.go.jabarprov.dbmpr.common.R.drawable.ic_damage_road,
                "Kerusakan Jalan"
            ),
            CategoryReportModel(
                1,
                id.go.jabarprov.dbmpr.common.R.drawable.ic_avalanche,
                "Bencana Alam"
            ),
        )
    }
}

enum class MakeReportScreenState {
    CATEGORY,
    PHOTO_VIDEO,
    DETAIL
}