package id.go.jabarprov.dbmpr.temanjabar.navigation

import android.content.Context
import android.content.Intent
import id.go.jabarprov.dbmpr.feature.authentication.presentation.activity.LoginActivity
import id.go.jabarprov.dbmpr.feature.authentication.presentation.activity.RegisterActivity
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.activity.DashboardActivity
import id.go.jabarprov.dbmpr.feature.news.presentation.activity.NewsActivity
import id.go.jabarprov.dbmpr.feature.report.presentation.activity.FormReportActivity
import id.go.jabarprov.dbmpr.feature.report.presentation.activity.ListReportActivity
import id.go.jabarprov.dbmpr.feature.report.presentation.activity.ReportActivity
import id.go.jabarprov.dbmpr.temanjabar.feature.map.presentation.activity.MapActivity
import id.go.jabarprov.dbmpr.temanjabar.navigation.auth.AuthNavigationModule
import id.go.jabarprov.dbmpr.temanjabar.navigation.dashboard.DashboardNavigationModule
import id.go.jabarprov.dbmpr.temanjabar.navigation.map.MapArgs
import id.go.jabarprov.dbmpr.temanjabar.navigation.news.NewsArgs
import id.go.jabarprov.dbmpr.temanjabar.navigation.report.ReportNavigationModule
import id.go.jabarprov.dbmpr.temanjabar.navigation.splash_screen.SplashScreenNavigationModule
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRouter @Inject constructor() : SplashScreenNavigationModule, DashboardNavigationModule,
    ReportNavigationModule, AuthNavigationModule {
    override fun goToDashboardScreen(context: Context) {
        val intent = Intent(context, DashboardActivity::class.java)
        context.startActivity(intent)
    }

    override fun goToLoginScreen(context: Context) {
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
    }

    override fun goToNewsScreen(context: Context, slug: String) {
        val intent = Intent(context, NewsActivity::class.java).apply {
            putExtra(NewsArgs.SLUG, slug)
        }
        context.startActivity(intent)
    }

    override fun goToFormReportScreen(context: Context) {
        val intent = Intent(context, FormReportActivity::class.java)
        context.startActivity(intent)
    }

    override fun goToMapScreen(context: Context, lat: Double?, long: Double?) {
        val intent = Intent(context, MapActivity::class.java).apply {
            putExtra(MapArgs.LAT, lat)
            putExtra(MapArgs.LONG, long)
        }
        context.startActivity(intent)
    }

    override fun goToListReportScreen(context: Context) {
        val intent = Intent(context, ListReportActivity::class.java)
        context.startActivity(intent)
    }

    override fun goToReport(context: Context) {
        val intent = Intent(context, ReportActivity::class.java)
        context.startActivity(intent)
    }

    override fun goToRegisterScreen(context: Context) {
        val intent = Intent(context, RegisterActivity::class.java)
        context.startActivity(intent)
    }
}