package id.go.jabarprov.dbmpr.temanjabar.navigation.dashboard

import android.content.Context

interface DashboardNavigationModule {
    fun goToNewsScreen(context: Context, slug: String)
    fun goToFormReportScreen(context: Context)
    fun goToMapScreen(context: Context, lat: Double? = null, long: Double? = null)
    fun goToListReportScreen(context: Context)
}