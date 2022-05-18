object DefaultConfig {
    const val applicationId = "id.go.jabarprov.dbmpr.temanjabar"
    const val minSdk = 28
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
    const val news = ":features:news"
    const val webview = ":features:webview"
    const val authentication = ":features:authentication"
    const val splashScreen = ":features:splash_screen"
    const val report = ":features:report"
}

object Common {
    const val common = ":common"
}

object Utils {
    const val utils = ":utils"
}

object Versions {
    const val gradleTools = "7.1.3"
    const val kotlinPlugin = "1.6.20"

    const val androidCore = "1.7.0"
    const val appCompat = "1.4.1"
    const val materialDesign = "1.5.0"
    const val constraintLayout = "2.1.3"
    const val swipeRefreshLayout = "1.1.0"
    const val dotsIndicator = "4.3"
    const val shimmer = "0.5.0"
    const val coil = "2.0.0-rc02"
    const val exoPlayer = "2.17.1"

    const val navigation = "2.4.1"

    const val hilt = "2.38.1"

    const val retrofit = "2.9.0"
    const val gsonConverter = "2.9.0"
    const val okHttp = "5.0.0-alpha.2"
    const val loggingInterceptor = "5.0.0-alpha.2"

    const val activityCompose = "1.4.0"
    const val materialCompose = "1.1.1"
    const val toolingCompose = "1.1.1"
    const val viewModelCompose = "2.4.1"
    const val composeCompiler = "1.2.0-alpha08"

    const val coroutineCore = "1.6.0"
    const val coroutineAndroid = "1.6.0"
    const val coroutinePlayServices = "1.6.1"

    const val viewModelScope = "2.4.0"
    const val lifecycleScope = "2.4.0"
    const val liveData = "2.4.0"

    const val arcgis = "100.13.0"

    const val googleService = "4.3.10"
    const val firebaseBom = "29.3.1"
    const val firebaseCore = "20.1.2"
    const val playServiceLocation = "19.0.1"
    const val playServiceAuth = "20.1.0"

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
    const val swipeRefreshLayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"
    const val dotsIndicator = "com.tbuonomo:dotsindicator:${Versions.dotsIndicator}"
    const val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmer}"
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val coilVideo = "io.coil-kt:coil-video:${Versions.coil}"
    const val exoPlayer = "com.google.android.exoplayer:exoplayer:${Versions.exoPlayer}"

    const val navigationSafeArgs =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

    const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.gsonConverter}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"

    const val coroutineCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutineCore}"
    const val coroutineAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutineAndroid}"
    const val coroutinePlayServices =
        "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.coroutinePlayServices}"

    const val viewModelScope =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModelScope}"
    const val lifecycleScope = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleScope}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.liveData}"

    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val materialCompose = "androidx.compose.material:material:${Versions.materialCompose}"
    const val toolingCompose = "androidx.compose.ui:ui-tooling:${Versions.toolingCompose}"
    const val viewModelCompose =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.viewModelCompose}"

    const val arcgis = "com.esri.arcgisruntime:arcgis-android:${Versions.arcgis}"

    const val googleService = "com.google.gms:google-services:${Versions.googleService}"
    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
    const val firebaseCore = "com.google.firebase:firebase-core:${Versions.firebaseCore}"
    const val firebaseAuth = "com.google.firebase:firebase-auth"

    const val playServiceLocation =
        "com.google.android.gms:play-services-location:${Versions.playServiceLocation}"
    const val playServiceAuth =
        "com.google.android.gms:play-services-auth:${Versions.playServiceAuth}"

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