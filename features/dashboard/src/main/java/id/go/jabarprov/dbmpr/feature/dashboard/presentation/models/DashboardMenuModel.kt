package id.go.jabarprov.dbmpr.feature.dashboard.presentation.models

import androidx.annotation.DrawableRes

data class DashboardMenuModel(
    val name: String,
    @DrawableRes val icon: Int,
    val type: DashboardMenuType
)

enum class DashboardMenuType {
    MAP,
    NEWS,
    REPORT,
    OTHER
}
