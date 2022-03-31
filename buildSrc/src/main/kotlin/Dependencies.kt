object DefaultConfig {
    const val applicationId = "id.go.jabarprov.dbmpr.temanjabar"
    const val minSdk = 23
    const val targetSdk = 32
    const val compileSdk = 32
}

object Releases {
    const val versionCode = 1
    const val versionName = "0.0.1 (Alpha)"
}

object Features {
    const val map = ":features:map"
    const val dashboard = ":features:dashboard"
}

object Versions {
    const val gradleTools = "7.1.2"
    const val kotlinPlugin = "1.6.10"

    const val androidCore = "1.7.0"
    const val appCompat = "1.4.1"
    const val materialDesign = "1.5.0"
    const val constraintLayout = "2.1.3"

    const val navigation = "2.4.1"

    const val activityCompose = "1.4.0"
    const val materialCompose = "1.1.1"
    const val toolingCompose = "1.1.1"
    const val viewModelCompose = "2.4.1"

    const val arcgis = "100.13.0"

    const val jUnit = "4.13.2"
    const val androidJUnit = "1.1.3"
    const val espresso = "3.4.0"
}

object Libs {
    const val gradleTools = "com.android.tools.build:gradle:${Versions.gradleTools}"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinPlugin}"

    const val androidCore = "androidx.core:core-ktx:${Versions.androidCore}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val materialDesign = "com.google.android.material:material:${Versions.materialDesign}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    const val navigationSafeArgs =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val materialCompose = "androidx.compose.material:material:${Versions.materialCompose}"
    const val toolingCompose = "androidx.compose.ui:ui-tooling:${Versions.toolingCompose}"
    const val viewModelCompose =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.viewModelCompose}"

    const val arcgis = "com.esri.arcgisruntime:arcgis-android:${Versions.arcgis}"

    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val androidJUnit = "androidx.test.ext:junit:${Versions.androidJUnit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}

object TemanjabarApp {
    const val android = ":app"
}

object Core {
    const val main = ":core:core_main"
    const val views = ":core:core_views"
}