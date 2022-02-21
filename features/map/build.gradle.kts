apply {
    from("$rootDir/common-android-library.gradle")
    plugin("kotlin-kapt")
}

val implementation by configurations

dependencies {
    implementation(Libs.androidCore)
    implementation(Libs.appCompat)
    implementation(Libs.materialDesign)
}

