package id.go.jabarprov.dbmpr.temanjabar.navigation

import android.content.Context
import android.content.Intent
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.activity.DashboardActivity
import id.go.jabarprov.dbmpr.feature.news.presentation.activity.NewsActivity
import id.go.jabarprov.dbmpr.feature.report.presentation.activity.FormReportActivity
import id.go.jabarprov.dbmpr.temanjabar.feature.map.presentation.activity.MapActivity
import id.go.jabarprov.dbmpr.temanjabar.navigation.dashboard.DashboardNavigationModule
import id.go.jabarprov.dbmpr.temanjabar.navigation.map.MapArgs
import id.go.jabarprov.dbmpr.temanjabar.navigation.news.NewsArgs
import id.go.jabarprov.dbmpr.temanjabar.navigation.splash_screen.SplashScreenNavigationModule
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRouter @Inject constructor() : SplashScreenNavigationModule, DashboardNavigationModule {
    override fun goToDashboardScreen(context: Context) {
        val intent = Intent(context, DashboardActivity::class.java)
        context.startActivity(intent)
    }

    override fun goToLoginScreen(context: Context) {
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
}