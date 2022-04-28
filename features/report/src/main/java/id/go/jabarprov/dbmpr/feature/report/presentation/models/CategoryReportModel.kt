package id.go.jabarprov.dbmpr.feature.report.presentation.models

import androidx.annotation.DrawableRes

data class CategoryReportModel(
    val id: Int,
    @DrawableRes
    val icon: Int,
    val description: String
)
