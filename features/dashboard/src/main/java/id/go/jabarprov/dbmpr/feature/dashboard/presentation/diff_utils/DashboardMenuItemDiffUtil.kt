package id.go.jabarprov.dbmpr.feature.dashboard.presentation.diff_utils

import androidx.recyclerview.widget.DiffUtil
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.models.DashboardMenuModel

class DashboardMenuItemDiffUtil : DiffUtil.ItemCallback<DashboardMenuModel>() {
    override fun areItemsTheSame(
        oldItem: DashboardMenuModel,
        newItem: DashboardMenuModel
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: DashboardMenuModel,
        newItem: DashboardMenuModel
    ): Boolean {
        return oldItem.name == newItem.name && oldItem.icon == newItem.icon
    }
}