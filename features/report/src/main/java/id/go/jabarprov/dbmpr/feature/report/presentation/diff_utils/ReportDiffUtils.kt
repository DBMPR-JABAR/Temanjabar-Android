package id.go.jabarprov.dbmpr.feature.report.presentation.diff_utils

import androidx.recyclerview.widget.DiffUtil
import id.go.jabarprov.dbmpr.feature.report.presentation.models.ReportModel

class ReportDiffUtils : DiffUtil.ItemCallback<ReportModel>() {
    override fun areItemsTheSame(oldItem: ReportModel, newItem: ReportModel): Boolean {
        return oldItem.imageUrl == newItem.imageUrl
    }

    override fun areContentsTheSame(oldItem: ReportModel, newItem: ReportModel): Boolean {
        return oldItem.imageUrl == newItem.imageUrl
    }
}