apply {
    from("$rootDir/common-android-library.gradle")
}

val implementation by configurations
val api by configurations

dependencies {
    implementation(project(Core.main))
    implementation(project(Core.views))

    implementation(Libs.androidCore)
    implementation(Libs.appCompat)
    implementation(Libs.materialDesign)
    implementation(Libs.constraintLayout)

    api(Libs.arcgis)
}

