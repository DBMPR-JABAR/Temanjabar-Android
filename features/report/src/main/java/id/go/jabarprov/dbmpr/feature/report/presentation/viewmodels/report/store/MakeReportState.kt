package id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.store

import id.go.jabarprov.dbmpr.core_main.store.State
import id.go.jabarprov.dbmpr.feature.report.presentation.models.CategoryReportModel
import id.go.jabarprov.dbmpr.feature.report.presentation.models.PhotoModel
import id.go.jabarprov.dbmpr.feature.report.presentation.models.VideoModel

data class MakeReportState(
    val reportPrivacy: MakeReportPrivacy = MakeReportPrivacy.PRIVATE,
    val currentListCategoryReport: List<CategoryReportModel> = LIST_CATEGORY_REPORT,
    val selectedCategory: CategoryReportModel? = null,
    val screenState: MakeReportScreenState = MakeReportScreenState.PRIVACY,
    val currentListPhoto: List<PhotoModel> = listOf(),
    val currentVideo: VideoModel? = null,
    val description: String? = null,
    val descriptionErrorMessage: String? = null,
    val location: String? = null,
    val locationErrorMessage: String? = null,
    val explanation: String? = null
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
    PRIVACY,
    CATEGORY,
    PHOTO_VIDEO,
    DETAIL
}

enum class MakeReportPrivacy {
    PRIVATE,
    PUBLIC
}