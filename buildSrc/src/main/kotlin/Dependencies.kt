object DefaultConfig {
    const val applicationId = "id.go.jabarprov.dbmpr.temanjabar"
    const val minSdk = 21
    const val targetSdk = 32
    const val compileSdk = 32
}

object Releases {
    const val versionCode = 1
    const val versionName = "0.0.1 (Alpha)"
}

object Features {
    const val map = ":features:map"
}

object Versions {
    const val gradleTools = "7.1.1"
    const val kotlinPlugin = "1.6.10"

    const val androidCore = "1.7.0"
    const val appCompat = "1.4.1"
    const val materialDesign = "1.5.0"

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

    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val androidJUnit = "androidx.test.ext:junit:${Versions.androidJUnit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}