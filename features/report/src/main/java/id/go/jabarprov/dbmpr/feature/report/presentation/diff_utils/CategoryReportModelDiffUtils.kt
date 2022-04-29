package id.go.jabarprov.dbmpr.feature.report.presentation.diff_utils

import androidx.recyclerview.widget.DiffUtil
import id.go.jabarprov.dbmpr.feature.report.presentation.models.CategoryReportModel

class CategoryReportModelDiffUtils : DiffUtil.ItemCallback<CategoryReportModel>() {
    override fun areItemsTheSame(
        oldItem: CategoryReportModel,
        newItem: CategoryReportModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CategoryReportModel,
        newItem: CategoryReportModel
    ): Boolean {
        return oldItem.isSelected == newItem.isSelected && oldItem.description == newItem.description && oldItem.icon == newItem.icon
    }
}