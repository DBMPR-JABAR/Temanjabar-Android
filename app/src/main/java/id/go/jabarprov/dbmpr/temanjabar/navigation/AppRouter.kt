package id.go.jabarprov.dbmpr.temanjabar.navigation

import android.content.Context
import android.content.Intent
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.activity.DashboardActivity
import id.go.jabarprov.dbmpr.feature.splash_screen.navigation.SplashScreenDirections
import id.go.jabarprov.dbmpr.temanjabar.navigation.splash_screen.SplashScreenNavigationModule
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRouter @Inject constructor() : SplashScreenNavigationModule {
    override fun goToDashboardScreen(context: Context) {
        val intent = Intent(context, DashboardActivity::class.java)
        context.startActivity(intent)
    }

    override fun goToLoginScreen(context: Context) {
    }
}