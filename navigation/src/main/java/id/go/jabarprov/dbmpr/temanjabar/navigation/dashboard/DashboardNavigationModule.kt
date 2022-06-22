package id.go.jabarprov.dbmpr.temanjabar.navigation.dashboard

import android.content.Context

interface DashboardNavigationModule {
    fun goToNewsScreen(context: Context, slug: String)
    fun goToFormReportScreen(context: Context)
}