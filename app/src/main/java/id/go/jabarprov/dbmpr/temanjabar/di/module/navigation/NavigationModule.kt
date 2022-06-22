package id.go.jabarprov.dbmpr.temanjabar.di.module.navigation

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.go.jabarprov.dbmpr.temanjabar.navigation.AppRouter
import id.go.jabarprov.dbmpr.temanjabar.navigation.dashboard.DashboardNavigationModule
import id.go.jabarprov.dbmpr.temanjabar.navigation.splash_screen.SplashScreenNavigationModule

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {
    @Binds
    abstract fun bindAppRouterToSplashScreenDirections(appRouter: AppRouter): SplashScreenNavigationModule

    @Binds
    abstract fun bindAppRouterToDashboardDirections(appRouter: AppRouter): DashboardNavigationModule
}